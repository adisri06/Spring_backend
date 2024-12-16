package com.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.backend.ReactBackendAppApplication;
import com.backend.dto.CustomerProjectionDTO;
import com.backend.exception.CommonExceptions;
import com.backend.exception.ExceptionMessages;
import com.backend.repository.CustomerPageableRepository;

@Service("customerPageableService")
@Transactional
public class CustomerPageableService implements CustomerService {
    
    public static final Log logger = LogFactory.getLog(ReactBackendAppApplication.class);
    public static final Logger paramLogger = LoggerFactory.getLogger(ReactBackendAppApplication.class);

    @Autowired
    CustomerPageableRepository customerPageableRepository;
    @Autowired
    CacheManager cacheManager;

    @Override
    @Cacheable(value = "customers", key = "#planId + '-' + #page + '-' + #size")
    public Page<CustomerProjectionDTO> filterByPlanIdPages(String planId, int page, int size) throws CommonExceptions {            
        try {
            // Create a Pageable instance with sorting by 'name' in ascending order
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
            
            // Fetch paginated and sorted results
            Page<CustomerProjectionDTO> customerList = customerPageableRepository.findByplanIDProjection(planId, pageable);

            // Log the total number of elements and details
            paramLogger.info("Total customers found: {}", customerList.getTotalElements());
            paramLogger.info("Page number: {}", customerList.getNumber());
            paramLogger.info("Page size: {}", customerList.getSize());

            // Check if the page contains any data
            if (customerList.isEmpty()) {
                logger.warn(ExceptionMessages.CUSTOMER_NOT_FOUND);
                throw new CommonExceptions(ExceptionMessages.CUSTOMER_NOT_FOUND);
            } else {
                // Log or process the customer data
                customerList.getContent().forEach(customer -> paramLogger.info("Customer: {}", customer));
                logger.info(ExceptionMessages.CUSTOMER_FOUND);
            }
            logger.info("Customer filtered successfully now checking for cache");

            // Retrieve customer names from the cache
            List<String> cachedCustomerNames = getCustomerNamesFromCache(planId, page, size);
            cachedCustomerNames.forEach(name -> paramLogger.info("Cached Customer Name: {}", name));

            return customerList;

        } catch (Exception e) {
            logger.error(ExceptionMessages.FAIL_MESSAGE, e);
            throw new CommonExceptions(ExceptionMessages.FAIL_MESSAGE, e);
        }
    }

    // Method to get customer names from cache
    public List<String> getCustomerNamesFromCache(String planId, int page, int size) throws CommonExceptions {
        String cacheName = "customers";  // The name of the cache to retrieve from

        // Retrieve the cache value (Page of customers) for the given planId, page, and size
        Page<CustomerProjectionDTO> cachedCustomerPage = getCacheValue(cacheName, planId, page, size);
        
        // If cache is empty or not found, fetch and populate cache
        if (cachedCustomerPage == null || cachedCustomerPage.isEmpty()) {
            paramLogger.info("Cache miss: Retrieving data from repository and caching.");
            // Fetch the data again if cache miss occurs
            cachedCustomerPage = fetchAndCacheCustomerData(planId, page, size);
        }

        // Extract and return the names from the cached content
        List<String> customerNames = cachedCustomerPage.getContent().stream()
                .map(CustomerProjectionDTO::getName)
                .collect(Collectors.toList());

        return customerNames;
    }

    // Fetch customer data and populate cache
    private Page<CustomerProjectionDTO> fetchAndCacheCustomerData(String planId, int page, int size) throws CommonExceptions {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
            Page<CustomerProjectionDTO> customerList = customerPageableRepository.findByplanIDProjection(planId, pageable);
            
            // Cache the fetched customer data
            cacheCustomerData(planId, page, size, customerList);
            return customerList;

        } catch (Exception e) {
            logger.error("Error while fetching customer data and populating cache", e);
            throw new CommonExceptions("Error while fetching customer data and populating cache", e);
        }
    }

    private void cacheCustomerData(String planId, int page, int size, Page<CustomerProjectionDTO> customerList) {
        String cacheKey = planId + "-" + page + "-" + size;
        Cache cache = cacheManager.getCache("customers");
        if (cache != null) {
            cache.put(cacheKey, customerList);
            paramLogger.info("Customer data cached with key: {}", cacheKey);
        } else {
            logger.error("Cache not found for customers");
        }
    }

    private Page<CustomerProjectionDTO> getCacheValue(String cacheName, String planId, int page, int size) {
        String cacheKey = planId + "-" + page + "-" + size;
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            paramLogger.warn("Cache not found: {}", cacheName);
            return null;
        }
        return cache.get(cacheKey, Page.class);  // Returns the cached page or null if not found
    }
}
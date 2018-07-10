package org.upgrad.services;

import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    public void updateCategory(String title,String description);
}

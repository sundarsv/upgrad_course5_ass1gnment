package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.Category;

import java.util.List;
/*
    Author - Kanishka
    Date Created - 9 July, 2018
    Description - Services for 'category' feature
 */
@Service
public interface CategoryService {
    void updateCategory(String title,String description);
    Iterable<Category> getAllCategories();
}

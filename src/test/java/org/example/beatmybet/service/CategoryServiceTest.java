package org.example.beatmybet.service;

import org.example.beatmybet.dto.CategoryDTO;
import org.example.beatmybet.entity.Category;
import org.example.beatmybet.exception.NotFoundException;
import org.example.beatmybet.repository.CategoryRepository;
import org.example.beatmybet.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SpringBootTest
@TestExecutionListeners(MockitoTestExecutionListener.class)
public class CategoryServiceTest {

    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;

    private static final Category SPORT = new Category();
    private static final Category FOOTBALL = new Category();
    private static final Category BASKETBALL = new Category();
    private static final Category AMERICAN_FOOTBALL = new Category();

    @BeforeEach
    public void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository);

        SPORT.setId(1L);
        SPORT.setName("sport");
        SPORT.setCategory(null);

        FOOTBALL.setId(2L);
        FOOTBALL.setName("football");
        FOOTBALL.setCategory(SPORT);

        BASKETBALL.setId(3L);
        BASKETBALL.setName("basketball");
        BASKETBALL.setCategory(SPORT);

        AMERICAN_FOOTBALL.setId(4L);
        AMERICAN_FOOTBALL.setName("american football");

        AMERICAN_FOOTBALL.setCategory(FOOTBALL);

        AMERICAN_FOOTBALL.setSubCategories(List.of());
        FOOTBALL.setSubCategories(List.of(AMERICAN_FOOTBALL));
        BASKETBALL.setSubCategories(List.of());
        SPORT.setSubCategories(List.of(FOOTBALL, BASKETBALL));

        List<Category> categories = List.of(SPORT, FOOTBALL, BASKETBALL, AMERICAN_FOOTBALL);
        doReturn(categories).when(categoryRepository).findAll();
        doReturn(Optional.of(SPORT)).when(categoryRepository).findById(SPORT.getId());
        doReturn(Optional.of(FOOTBALL)).when(categoryRepository).findById(FOOTBALL.getId());
    }

    private CategoryDTO getCategoryDto(long id, String name, List<CategoryDTO> subCategories) {
        return CategoryDTO.builder()
                .id(id)
                .name(name)
                .subCategories(subCategories)
                .build();
    }

    @Test
    public void getAllCategoriesForHomePage() {
        System.out.println(FOOTBALL);
        CategoryDTO footballDto = getCategoryDto(FOOTBALL.getId(), FOOTBALL.getName(), null);
        CategoryDTO basketballDto = getCategoryDto(BASKETBALL.getId(), BASKETBALL.getName(), null);
        CategoryDTO sportDto = getCategoryDto(SPORT.getId(), SPORT.getName(), List.of(footballDto, basketballDto));

        List<CategoryDTO> expected = List.of(sportDto);
        List<CategoryDTO> actual = categoryService.getMainCategories();

        assertEquals(expected, actual);
    }

    @Test
    public void getSubcategories() {
        final long ID_SPORT = SPORT.getId();
        final long ID_FOOTBALL = FOOTBALL.getId();
        final long ID_NO_EXIST = 100L;

        CategoryDTO americanFootballDto1 = getCategoryDto(AMERICAN_FOOTBALL.getId(), AMERICAN_FOOTBALL.getName(), null);
        CategoryDTO footballDto = getCategoryDto(FOOTBALL.getId(), FOOTBALL.getName(), List.of(americanFootballDto1));
        CategoryDTO basketballDto = getCategoryDto(BASKETBALL.getId(), BASKETBALL.getName(), List.of());

        List<CategoryDTO> expectedSport = List.of(footballDto, basketballDto);
        List<CategoryDTO> actualSport = categoryService.getSubcategories(ID_SPORT);

        CategoryDTO americanFootballDto2 = getCategoryDto(AMERICAN_FOOTBALL.getId(), AMERICAN_FOOTBALL.getName(), List.of());
        americanFootballDto2.setSubCategories(List.of());
        List<CategoryDTO> expectedFootball = List.of(americanFootballDto2);
        List<CategoryDTO> actualFootball = categoryService.getSubcategories(ID_FOOTBALL);

        assertEquals(expectedSport, actualSport);
        assertEquals(expectedFootball, actualFootball);
        assertThrows(NotFoundException.class, () -> categoryService.getSubcategories(ID_NO_EXIST));
    }

    @Test
    public void getById() {
        final long ID_SPORT = SPORT.getId();
        final long ID_NO_EXIST = 100L;

        CategoryDTO footballDto = getCategoryDto(FOOTBALL.getId(), FOOTBALL.getName(), null);
        CategoryDTO basketballDto = getCategoryDto(BASKETBALL.getId(), BASKETBALL.getName(), null);
        CategoryDTO sportDto = getCategoryDto(SPORT.getId(), SPORT.getName(), List.of(footballDto, basketballDto));

        CategoryDTO actual = categoryService.getById(ID_SPORT);

        assertEquals(sportDto, actual);
        assertThrows(NotFoundException.class, () -> categoryService.getById(ID_NO_EXIST));
    }
}

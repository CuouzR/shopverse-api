package com.shopverse.shopverseapi.batch;



import com.shopverse.shopverseapi.batch.model.CategoryCsv;
import com.shopverse.shopverseapi.batch.model.ProductCsv;
import com.shopverse.shopverseapi.models.Category;
import com.shopverse.shopverseapi.models.Product;
import com.shopverse.shopverseapi.repository.CategoryRepository;
import com.shopverse.shopverseapi.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public BatchConfig(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    // 🧾 Lector de CSV de categorías
    @Bean
    public FlatFileItemReader<CategoryCsv> categoryReader() {
        return new FlatFileItemReaderBuilder<CategoryCsv>()
                .name("categoryReader")
                .resource(new ClassPathResource("data/categories.csv"))
                .linesToSkip(1) // ← ignora encabezado del CSV
                .delimited()
                .names("name", "description")
                .targetType(CategoryCsv.class)
                .build();
    }

    // 🧾 Lector de CSV de productos
    @Bean
    public FlatFileItemReader<ProductCsv> productReader() {
        return new FlatFileItemReaderBuilder<ProductCsv>()
                .name("productReader")
                .resource(new ClassPathResource("data/products.csv"))
                .linesToSkip(1)
                .delimited()
                .names("name", "description", "price", "category_id")
                .targetType(ProductCsv.class)
                .build();
    }

    //  Procesador: convierte CategoryCsv en entidad Category
    @Bean
    public ItemProcessor<CategoryCsv, Category> categoryProcessor() {
        return csv -> new Category(csv.getName(), csv.getDescription());
    }

    //  Procesador: convierte ProductCsv en entidad Product, asociando su categoría
    @Bean
    public ItemProcessor<ProductCsv, Product> productProcessor() {
        return csv -> {
            Category category = categoryRepository.findById(csv.getCategory_id())
                    .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada: " + csv.getCategory_id()));
            return new Product( csv.getName(), csv.getDescription(), csv.getPrice(), category);
        };
    }

    //  Guardado de categorías
    @Bean
    public ItemWriter<Category> categoryWriter() {
        return categoryRepository::saveAll;
    }

    //  Guardado de productos
    @Bean
    public ItemWriter<Product> productWriter() {
        return productRepository::saveAll;
    }

    // Paso 1: importar categorías
    @Bean
    public Step importCategoriesStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("importCategoriesStep", jobRepository)
                .<CategoryCsv, Category>chunk(10, transactionManager)
                .reader(categoryReader())
                .processor(categoryProcessor())
                .writer(categoryWriter())
                .build();
    }

    // Paso 2: importar productos
    @Bean
    public Step importProductsStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("importProductsStep", jobRepository)
                .<ProductCsv, Product>chunk(10, transactionManager)
                .reader(productReader())
                .processor(productProcessor())
                .writer(productWriter())
                .build();
    }

    // Job completo
    @Bean
    public Job importCatalogJob(JobRepository jobRepository, Step importCategoriesStep, Step importProductsStep) {
        return new JobBuilder("importCatalogJob", jobRepository)
                .start(importCategoriesStep)
                .next(importProductsStep)
                .build();
    }
}

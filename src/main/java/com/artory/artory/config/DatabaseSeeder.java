package com.artory.artory.config;

import com.artory.artory.entity.Product;
import com.artory.artory.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DatabaseSeeder {

        @Bean
        public CommandLineRunner initData(ProductRepository productRepository,
                        com.artory.artory.repository.OrderRepository orderRepository,
                        com.artory.artory.repository.CustomerRepository customerRepository,
                        com.artory.artory.repository.ContactMessageRepository contactRepository) {
                return args -> {
                        orderRepository.deleteAll();
                        customerRepository.deleteAll();
                        contactRepository.deleteAll();
                        productRepository.deleteAll(); // Clear existing wrong IT products
                        System.out.println("Cleared old products. Seeding paintings...");

                        Product p1 = new Product(null, "Ethereal Echo",
                                        "A mesmerizing blend of colors that evokes a sense of deep tranquility and inner peace.",
                                        3500.0, "Pictures/1.png.jpeg");
                        Product p2 = new Product(null, "Silent Bloom",
                                        "Soft contrasts and dynamic strokes capturing the quiet, beautiful awakening of nature.",
                                        4200.0, "Pictures/2.png.jpeg");
                        Product p3 = new Product(null, "Midnight Reverie",
                                        "Bold textures and deep hues creating a striking visual narrative of the mystic night sky.",
                                        5000.0, "Pictures/3.png.jpeg");
                        Product p4 = new Product(null, "Golden Cascade",
                                        "A radiant array of warm tones cascading down the canvas to bring light to any room.",
                                        6500.0, "Pictures/4.png.jpeg");
                        Product p5 = new Product(null, "Whispering Woods",
                                        "An abstract exploration of forest depths, textured with rich greens and earthly browns.",
                                        3800.0, "Pictures/5.png.jpeg");
                        Product p6 = new Product(null, "Crimson Horizon",
                                        "A passionate exploration of red hues that commands attention and inspires bold thoughts.",
                                        7000.0, "Pictures/6.png.jpeg");
                        Product p7 = new Product(null, "Oceanic Dreams",
                                        "Serene blues and flowing lines that perfectly mimic the gentle yet powerful waves of the sea.",
                                        4800.0, "Pictures/7.png.jpeg");
                        Product p8 = new Product(null, "Urban Symphony",
                                        "A chaotic yet beautiful blend of sharp angles and energetic colors representing city life.",
                                        6200.0, "Pictures/8.png.jpeg");
                        Product p9 = new Product(null, "Pastel Illusion",
                                        "Soft, blurred pastels that transport the viewer to a nostalgic, dreamy state of mind.",
                                        5500.0, "Pictures/9.png.jpeg");
                        Product p10 = new Product(null, "Monochrome Minimal",
                                        "Sleek, modern, and deeply impactful minimalist canvas utilizing only stark contrasts.",
                                        4500.0, "Pictures/10.png.jpeg");

                        productRepository.saveAll(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));
                        System.out.println("Seeded 10 painting products.");
                };
        }
}

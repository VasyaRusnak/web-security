package com.example.demo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccessTest {

    @Autowired
    private MockMvc mockMvc;

    // 1. Анонімний доступ до відкритого роута (permitAll) -> Очікуємо 200 OK
    @Test
    @WithAnonymousUser
    void whenAnonymousAccessIndex_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/items/index"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Security (Permit All)!"));
    }

    // 2. Анонімний доступ до захищеного роута -> Очікуємо 401 Unauthorized
    @Test
    @WithAnonymousUser
    void whenAnonymousAccessAdmin_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/items/hello/admin"))
                .andExpect(status().isUnauthorized());
    }

    // 3. Доступ ADMIN до роута адміна -> Очікуємо 200 OK
    @Test
    @WithMockUser(roles = "ADMIN")
    void whenAdminAccessAdminRoute_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/items/hello/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Admin!"));
    }

    // 4. Доступ USER до роута адміна -> Очікуємо 403 Forbidden
    @Test
    @WithMockUser(roles = "USER")
    void whenUserAccessAdminRoute_thenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/items/hello/admin"))
                .andExpect(status().isForbidden());
    }

    // 5. Доступ USER до роута юзера -> Очікуємо 200 OK
    @Test
    @WithMockUser(roles = "USER")
    void whenUserAccessUserRoute_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/items/hello/user"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello User!"));
    }

    // 6. Доступ ADMIN до роута юзера -> Очікуємо 403 Forbidden
    @Test
    @WithMockUser(roles = "ADMIN")
    void whenAdminAccessUserRoute_thenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/items/hello/user"))
                .andExpect(status().isForbidden());
    }

    // 7. Доступ ADMIN до спільного роута (ADMIN, SUPERADMIN) -> Очікуємо 200 OK
    @Test
    @WithMockUser(roles = "ADMIN")
    void whenAdminAccessManRoute_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/items/hello/man"))
                .andExpect(status().isOk());
    }

    // 8. Доступ SUPERADMIN до спільного роута (ADMIN, SUPERADMIN) -> Очікуємо 200 OK
    @Test
    @WithMockUser(roles = "SUPERADMIN")
    void whenSuperAdminAccessManRoute_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/items/hello/man"))
                .andExpect(status().isOk());
    }

    // 9. Доступ USER до спільного роута (ADMIN, SUPERADMIN) -> Очікуємо 403 Forbidden
    @Test
    @WithMockUser(roles = "USER")
    void whenUserAccessManRoute_thenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/items/hello/man"))
                .andExpect(status().isForbidden());
    }

    // 10. Доступ будь-якого авторизованого користувача до базового захищеного роута -> Очікуємо 200 OK
    @Test
    @WithMockUser(roles = "USER") // Або будь-яка інша роль
    void whenAuthenticatedUserAccessUnknownRoute_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/items/unknown"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Unknown Route (Just Authenticated)!"));
    }
}

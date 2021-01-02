package info.wonlee.assessment.genesys.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.wonlee.assessment.genesys.player.Player;
import info.wonlee.assessment.genesys.player.PlayerQueue;
import info.wonlee.assessment.genesys.security.SimpleUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

@WebMvcTest(controllers = PlayerController.class)
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PlayerQueue playerQueue;

    @MockBean // this is for jwtRequestFilter
    SimpleUserDetailsService simpleUserDetailsService;

    @Test
    public void test_registration() throws Exception {
        Player player = new Player();
        player.setName("player");
        player.setLastCheckedIn(null);
        ObjectMapper objectMapper = new ObjectMapper();
        final String jsonBody = objectMapper.writeValueAsString(player);

        when(playerQueue.register(player)).thenReturn(player);

        final MvcResult mvcResult = this.mockMvc.perform(
                post("/api/v1/player/register")
                        .content(jsonBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final String contentAsString = mvcResult.getResponse().getContentAsString();
        System.out.println(contentAsString);
        Assertions.assertTrue(contentAsString.startsWith("ey"));
    }
}

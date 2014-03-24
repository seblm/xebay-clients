package fr.xebia.xebay.client.http;

import fr.xebia.xebay.domain.BidOffer;
import fr.xebia.xebay.domain.PublicUser;
import fr.xebia.xebay.domain.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.ProcessingException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class RestBidderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private RestBidder restBidder;

    @Before
    public void createRestBidder() {
        String target = "http://localhost:8080/rest";
        restBidder = new RestBidder(target);
        try {
            restBidder.getCurrentOffer();
        } catch (ProcessingException e) {
            System.out.format("Please start a bid server on %s%n", target);
            throw e;
        }
    }

    @Test
    public void should_register() {
        String apiKey = null;
        try {
            apiKey = restBidder.register("email@provider.net");

            assertThat(apiKey).isNotNull();
        } finally {
            if (apiKey != null)
                restBidder.unregister(apiKey);
        }
    }

    @Test
    public void should_unregister() {
        String apiKey = restBidder.register("email@provider.net");

        restBidder.unregister(apiKey);

        thrown.expect(NotAuthorizedException.class);
        restBidder.getUserInfo(apiKey);
    }

    @Test
    public void should_get_info() {
        String apiKey = null;
        try {
            apiKey = restBidder.register("email@provider.net");

            User userInfo = restBidder.getUserInfo(apiKey);

            assertThat(userInfo.getName()).isEqualTo("email@provider.net");
            assertThat(userInfo.getBalance()).isEqualTo(1000);
            assertThat(userInfo.getItems()).isEmpty();
            assertThat(userInfo.getKey()).isEqualTo(apiKey);
        } finally {
            if (apiKey != null)
                restBidder.unregister(apiKey);
        }
    }

    @Test
    public void should_get_users() {
        String apiKey = null;
        try {
            apiKey = restBidder.register("email@provider.net");

            Set<PublicUser> users = restBidder.getPublicUsers();

            assertThat(users).hasSize(1).containsOnly(new PublicUser("email@provider.net", 1000, 0));
        } finally {
            if (apiKey != null) {
                restBidder.unregister(apiKey);
            }
        }
    }

    @Test
    public void should_get_all_users() {
        String apiKey = null;
        try {
            apiKey = restBidder.register("email@provider.net");

            Set<User> users = restBidder.getUsers();

            assertThat(users).hasSize(1).extracting("name", "key", "balance").containsOnly(tuple("email@provider.net", apiKey, 1000d));
        } finally {
            if (apiKey != null) {
                restBidder.unregister(apiKey);
            }
        }
    }

    @Test
    public void should_get_current_bid_offer() {
        BidOffer currentOffer = restBidder.getCurrentOffer();

        assertThat(currentOffer.getItem()).isNotNull();
        assertThat(currentOffer.getTimeToLive()).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void should_bid() {
        String apiKey = null;
        try {
            apiKey = restBidder.register("email@provider.net");
            BidOffer currentBidOffer = restBidder.getCurrentOffer();
            double firstValue = currentBidOffer.getItem().getValue();
            double newValue = firstValue * 1.1;

            BidOffer bidOffer = restBidder.bidForm(currentBidOffer.getItem().getName(), newValue, apiKey);

            assertThat(bidOffer.getBidder()).isEqualTo("email@provider.net");
            assertThat(bidOffer.getItem().getValue()).isEqualTo(newValue);
        } finally {
            if (apiKey != null) {
                restBidder.unregister(apiKey);
            }
        }
    }
}

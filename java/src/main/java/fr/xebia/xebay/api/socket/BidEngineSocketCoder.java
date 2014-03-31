package fr.xebia.xebay.api.socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.*;
import java.io.IOException;

public class BidEngineSocketCoder implements Encoder.Text<BidEngineSocketInput>, Decoder.Text<BidEngineSocketOutput> {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public String encode(BidEngineSocketInput input) throws EncodeException {
        try {
            return objectMapper.writeValueAsString(input);
        } catch (IOException e) {
            throw new EncodeException(input, "", e);
        }
    }

    @Override
    public BidEngineSocketOutput decode(String input) throws DecodeException {
        try {
            return objectMapper.readValue(input, BidEngineSocketOutput.class);
        } catch (IOException e) {
            throw new DecodeException(input, "", e);
        }
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void destroy() {
    }
}

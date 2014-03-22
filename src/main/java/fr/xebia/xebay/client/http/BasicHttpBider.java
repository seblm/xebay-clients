package fr.xebia.xebay.client.http;

public class BasicHttpBider {
    private RestBidder restBidder;

    public BasicHttpBider() {
        restBidder = new RestBidder("http://localhost:8080/rest");

        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // ignore
            }
            System.out.println(restBidder.getCurrentOffer());
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Error: no api key has been provided.\n" +
                    "Usage: java " + BasicHttpBider.class.getName() + " API_KEY");
            System.exit(1);
        }

        new BasicHttpBider();
    }
}

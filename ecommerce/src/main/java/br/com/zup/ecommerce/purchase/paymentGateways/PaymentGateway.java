package br.com.zup.ecommerce.purchase.paymentGateways;

import br.com.zup.ecommerce.purchase.Purchase;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public enum PaymentGateway {

    pagseguro {
        @Override
        public String createResponseUrl(Purchase purchase, UriComponentsBuilder uriComponentsBuilder) {
            UriComponents pagseguroResponseUrl = uriComponentsBuilder.path("/retorno-pagseguro/{id}").buildAndExpand(purchase.getId().toString());

            return "pagseguro.com/" + purchase.getId() + "?redirectUrl=" + pagseguroResponseUrl;
        }
    },
    paypal {
        @Override
        public String createResponseUrl(Purchase purchase, UriComponentsBuilder uriComponentsBuilder) {
            UriComponents paypalResponseUrl = uriComponentsBuilder.path("/retorno-paypal/{id}").buildAndExpand(purchase.getId().toString());

            return "paypal.com/" + purchase.getId() + "?redirectUrl=" + paypalResponseUrl;
        }
    };

    public abstract String createResponseUrl(Purchase purchase, UriComponentsBuilder uriComponentsBuilder);
}


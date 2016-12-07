
package org.bank.credit.web.service;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "CreditScoreService", targetNamespace = "http://service.web.credit.bank.org/", wsdlLocation = "http://139.59.154.97:8080/CreditScoreService/CreditScoreService?wsdl")
public class CreditScoreService_Service
    extends Service
{

    private final static URL CREDITSCORESERVICE_WSDL_LOCATION;
    private final static WebServiceException CREDITSCORESERVICE_EXCEPTION;
    private final static QName CREDITSCORESERVICE_QNAME = new QName("http://service.web.credit.bank.org/", "CreditScoreService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://139.59.154.97:8080/CreditScoreService/CreditScoreService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CREDITSCORESERVICE_WSDL_LOCATION = url;
        CREDITSCORESERVICE_EXCEPTION = e;
    }

    public CreditScoreService_Service() {
        super(__getWsdlLocation(), CREDITSCORESERVICE_QNAME);
    }

    public CreditScoreService_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), CREDITSCORESERVICE_QNAME, features);
    }

    public CreditScoreService_Service(URL wsdlLocation) {
        super(wsdlLocation, CREDITSCORESERVICE_QNAME);
    }

    public CreditScoreService_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CREDITSCORESERVICE_QNAME, features);
    }

    public CreditScoreService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CreditScoreService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns CreditScoreService
     */
    @WebEndpoint(name = "CreditScoreServicePort")
    public CreditScoreService getCreditScoreServicePort() {
        return super.getPort(new QName("http://service.web.credit.bank.org/", "CreditScoreServicePort"), CreditScoreService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CreditScoreService
     */
    @WebEndpoint(name = "CreditScoreServicePort")
    public CreditScoreService getCreditScoreServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://service.web.credit.bank.org/", "CreditScoreServicePort"), CreditScoreService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CREDITSCORESERVICE_EXCEPTION!= null) {
            throw CREDITSCORESERVICE_EXCEPTION;
        }
        return CREDITSCORESERVICE_WSDL_LOCATION;
    }

}

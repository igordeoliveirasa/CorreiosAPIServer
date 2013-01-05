/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.igordeoliveirasa.correios.CorreiosAPI;
import br.com.igordeoliveirasa.correios.ZIPCodeAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author igor
 */
@Resource
public class CorreiosAPIController {
    
    private Result result;
    
    class DefaultVO {
        private int statusCode;
        private ZIPCodeAddress zipCodeAddress;
        private String copyright = "Desenvolvido por: Igor de Oliveira Sá - igordeoliveirasa@gmail.com";

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public ZIPCodeAddress getZipCodeAddress() {
            return zipCodeAddress;
        }

        public void setZipCodeAddress(ZIPCodeAddress zipCodeAddress) {
            this.zipCodeAddress = zipCodeAddress;
        }
    }
    
    public CorreiosAPIController(Result result) {
        this.result = result;
    }
    
    @Get("/getAddress/{zipCode}")
    public void getAddressFromZIPCode(String zipCode) {
        DefaultVO vo = new DefaultVO();
        vo.setStatusCode(0);
        
        try 
        {
            ZIPCodeAddress address = CorreiosAPI.getAddressByZipCode(zipCode);
            vo.setStatusCode(1);
            vo.setZipCodeAddress(address);
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(CorreiosAPIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        result.use(Results.json()).withoutRoot().from(vo).include("zipCodeAddress").serialize();        
    }
}

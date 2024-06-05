package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

import javax.swing.plaf.metal.MetalButtonUI;

public class Invoice {
    private Map<Product, Integer> products = new HashMap<Product, Integer>();
    private int number = 0;

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        Boolean flag = false;
        for (Product element : products.keySet()) {
            if (element.getName().equals(product.getName())) {
                try{
                    products.replace(element,products.get(element)+quantity);
                    flag = true;
                }catch (Exception e){

                }

            }
        }
        if(flag.equals(false)){
            products.put(product, quantity);
        }
    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

    public int getNumber() {
        return number;
    }

    public String printInvoice() {
        String stringInvoice = "";
        for (Map.Entry<Product, Integer> itr : products.entrySet()) {
            stringInvoice += itr.getKey().getName() + " " + itr.getValue().toString() + " " + itr.getKey().getPrice() + "\n";
        }
        return stringInvoice.substring(0,stringInvoice.length()-1);
    }
}

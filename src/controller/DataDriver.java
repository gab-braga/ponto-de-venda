package controller;

import model.Cliente;
import model.Produto;

public interface DataDriver {

    void insertAndFillClient(Cliente cliente);

    void insertAndFillProduct(Produto produto);
}

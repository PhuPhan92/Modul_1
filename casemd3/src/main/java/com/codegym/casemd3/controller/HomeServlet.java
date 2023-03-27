package controller;


import config.ResourceConfig;
import model.Product;
import service.IProductService;
import service.mySQL.MSProductService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = "")
public class HomeServlet extends HttpServlet {
    private IProductService iProductService;

    @Override
    public void init() throws ServletException {
        iProductService = new MSProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> productListPhone = iProductService.getAllProductByCategoryId(1);
        List<Product> productListTablet = iProductService.getAllProductByCategoryId(2);

        req.setAttribute("productListPhone", productListPhone);
        req.setAttribute("productListTablet", productListTablet);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ResourceConfig.folderFrontEnd + "index.jsp");
        requestDispatcher.forward(req, resp);
    }
}

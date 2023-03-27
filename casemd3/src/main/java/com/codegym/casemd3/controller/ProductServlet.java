package com.codegym.casetemplate.controller;

import com.codegym.casetemplate.model.Category;
import com.codegym.casetemplate.model.Customer;
import com.codegym.casetemplate.model.CustomerType;
import com.codegym.casetemplate.model.Product;
import com.codegym.casetemplate.service.ICategoryService;
import com.codegym.casetemplate.service.ICustomerService;
import com.codegym.casetemplate.service.ICustomerTypeService;
import com.codegym.casetemplate.service.IProductService;
import com.codegym.casetemplate.service.mysql.MSCategoryService;
import com.codegym.casetemplate.service.mysql.MSCustomerService;
import com.codegym.casetemplate.service.mysql.MSCustomerTypeService;
import com.codegym.casetemplate.service.mysql.MSProductService;
import com.codegym.casetemplate.utils.DateUtils;
import com.codegym.casetemplate.utils.ValidateUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = "/products")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ProductServlet extends HttpServlet{
    private IProductService iProductService;
    private ICategoryService iCategoryService;

    @Override
    public void init() throws ServletException {
        iProductService = new MSProductService();
        iCategoryService = new MSCategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showCreateProducts(req, resp);
                break;
            case "delete":
                showDeleteProducts(req, resp);
                break;
            case "edit":
                showEditProducts(req, resp);
                break;
            default:
                showProducts(req, resp);
        }
    }

    private void showProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = iProductService.getAllProduct();
        List<Category> categories = iCategoryService.getAllCategorys();
//        customers.size()
        req.setAttribute("products", products);
        req.setAttribute("category", categories );

        String message = req.getParameter("message");
        if (message != null) {
            // delete: success
            req.setAttribute("message", message );
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/dashboard/product/products.jsp");
        requestDispatcher.forward(req, resp);
    }


    private void showEditProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categories = iCategoryService.getAllCategorys();
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = iProductService.findProductById((long) id);

        if (product == null) {
            resp.sendRedirect("/customers?message=edit");
        }else{

            req.setAttribute("product", product);
            req.setAttribute("category", categories);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("dashboard/product/edit.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    private void showDeleteProducts(HttpServletRequest req, HttpServletResponse resp) {
    }

    private void showCreateProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categories = iCategoryService.getAllCategorys();
        req.setAttribute("category", categories);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/dashboard/product/create.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                createProducts(req, resp);
                break;
            case "delete":
                deleteProducts(req, resp);
                break;
            case "edit":
                editProducts(req, resp);
                break;
        }
    }

    private void editProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        Product product = new Product();

        isValidName(req, product, errors);
        product.setDescription(req.getParameter("description"));
        product.setPrice(Double.parseDouble(req.getParameter("price")));
        product.setIdCategory(Integer.parseInt(req.getParameter("category")));
        Part part = isValidImage(req, product, errors);
        String sCreatedAt = req.getParameter("createdAt");
        Date createAt = DateUtils.formatDate(sCreatedAt);
        product.setCreateAt(createAt);
//        errors.size()
        List<Category> categories = iCategoryService.getAllCategorys();
        req.setAttribute("category", categories);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("dashboard/product/edit.jsp");
        if (errors.size() == 0) {
            long idCustomer = Long.parseLong(req.getParameter("id"));
            product.setId(idCustomer);
//            handleEditImageUpload(req, customer, errors);
            Product productDB = iProductService.findProductById(product.getId());
            if ( product.getImage()!=null) {
                if(productDB.getImage() != null && !productDB.getImage().equals(product.getImage()) && part != null){
                    handleEditImageUploadAdvanced(part);
                }
            }
            req.setAttribute("message", "Them thanh cong");
            iProductService.editProduct(product);

            resp.sendRedirect("/products");
        }else{
            req.setAttribute("errors", errors);
            req.setAttribute("product", product);
            requestDispatcher.forward(req, resp);
        }
    }
    private void handleEditImageUploadAdvanced(Part part) throws IOException {

        String fileName = extractFileName(part);
        String appRealPath = getServletContext().getRealPath("/") + "images";
        File file = new File(appRealPath);
        if (!file.exists()) {
            file.mkdir();
        }
        String nameFileServer = appRealPath + File.separator + fileName;
        System.out.println("Name file server: " + nameFileServer);
        part.write(nameFileServer);
        String nameFileProject = "C:\\Phu Phan\\Demo\\C1022H1\\Module3-C1022H1\\casetemplate\\src\\main\\webapp\\images" + File.separator + fileName;
        System.out.println("Name file project: " + nameFileProject);
        part.write(nameFileProject);

    }

    private void deleteProducts(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("idDelete"));
        iProductService.deleteProductById(id);
        resp.sendRedirect("/products?message=delete");
    }

    private void createProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        Product product = new Product();

        isValidName(req, product, errors);
        String sCreatedAt = req.getParameter("createdAt");
        Date createAt = DateUtils.formatDate(sCreatedAt);
        product.setCreateAt(createAt);
        product.setDescription(req.getParameter("description"));
        product.setPrice(Double.parseDouble(req.getParameter("price")));
        product.setIdCategory(Integer.parseInt(req.getParameter("category")));
        List<Category> categories = iCategoryService.getAllCategorys();
        req.setAttribute("category", categories);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("dashboard/product/create.jsp");
        if (errors.size() == 0) {
            handleImageUpload(req, product, errors);
            req.setAttribute("message", "Them thanh cong");
            iProductService.createProduct(product);
            requestDispatcher.forward(req, resp);
        }else{
            req.setAttribute("errors", errors);
            req.setAttribute("customer", product);
            requestDispatcher.forward(req, resp);
        }
    }
    private void handleImageUpload(HttpServletRequest req, Product product, List<String> errors) throws ServletException, IOException {
        for (Part part : req.getParts()) {
            String fileName = extractFileName(part);
            // refines the fileName in case it is an absolute path
            if(!fileName.equals("")){
                String appRealPath = getServletContext().getRealPath("/") + "images";
                File file = new File(appRealPath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String nameFileServer = appRealPath + File.separator + fileName;
                System.out.println("Name file server: " + nameFileServer);
                part.write(nameFileServer);


                String nameFileProject = "C:\\Phu Phan\\Demo\\C1022H1\\Module3-C1022H1\\casetemplate\\src\\main\\webapp\\images" + File.separator + fileName;
                System.out.println("Name file project: " + nameFileProject);
                part.write(nameFileProject);

                product.setImage(fileName);
            }

//            part.write();
        }

    }
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
    private void isValidName(HttpServletRequest req, Product product, List<String> errors) {
        String name = req.getParameter("name");
        if (!ValidateUtils.isNameValid(name)) {
            errors.add("Tên không hợp lệ. Chỉ chứa từ từ 5-10 kí và bắt đầu A-Za-z0-9");
        }
        product.setName(name);
    }
    private Part isValidImage(HttpServletRequest req, Product product, List<String> errors) throws ServletException, IOException {
        for (Part part : req.getParts()) {
            String fileName = extractFileName(part);
            if(!fileName.equals("")){
                product.setImage(fileName);
                boolean check = iProductService.checkImageExists(fileName);
                if (check == true) {
                    return null;
                }else {
                    return part;
                }
            }
        }
        return null;
    }
}
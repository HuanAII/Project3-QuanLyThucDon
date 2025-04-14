import java.util.List;
import com.example.models.Category;

import com.example.dao.categoryDAO;

public class test {
    public static void main(String[] args) {
        List<Category> listDanhMuc = categoryDAO.getAllCategory();
        for (Category a : listDanhMuc){
            a.getId_danhmuc();
            a.getName_danhmuc();
        }
    }
    
}

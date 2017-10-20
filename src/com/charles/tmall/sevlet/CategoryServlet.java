package com.charles.tmall.sevlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.charles.tmall.bean.Category;
import com.charles.tmall.utils.Page;

import tmall.util.ImageUtil;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet(name="CategoryServlet",urlPatterns="/categoryServlet")
public class CategoryServlet extends BaseBackServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see BaseBackServlet#BaseBackServlet()
     */
    public CategoryServlet() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	/**
	 * 增删改 之后重新查
	 */

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		Map<String,String> params = new HashMap<>();
		InputStream is = super.parseUpload(request, params);
		String name = params.get("name");
		Category c = new Category();
		c.setName(name);
		categoryDao.add(c);
		File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
		File file = new File(imageFolder,c.getId()+".jpg");
		try{
			
			if(is!=null&&is.available()!=0){
				FileOutputStream fos = new FileOutputStream(file);
				byte[] b = new byte[1024*1024];
				int length =0;
				while((length=is.read(b))!=-1){
					fos.write(b,0,length);
				}
				fos.flush();
				BufferedImage img = ImageUtil.change2jpg(file);
				ImageIO.write(img, "jpg", file);
				fos.close();
			}
			 
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "@admin_category_list";
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		categoryDao.delete(id);
		return "@admin_category_list";
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Category c = categoryDao.get(id);
		request.setAttribute("c", c);
		return "admin/editCategory.jsp";
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		Map<String,String> params = new HashMap<>();
		InputStream is = super.parseUpload(request, params);
		System.out.println(params);
		String name = params.get("name");
		int id = Integer.parseInt(params.get("id"));
		Category c = new Category();
		c.setId(id);
		c.setName(name);
		categoryDao.update(c);
		
		File imageFolder= new File(request.getSession().getServletContext().getRealPath("img/category"));
		File file = new File(imageFolder,c.getId()+".jpg");
		file.getParentFile().mkdirs();
		try{
			if(is!=null && is.available()!=0){
				FileOutputStream fos = new FileOutputStream(file);
				byte[] b = new byte[1024*1024];
				int length=0;
				while((length =is.read(b))!=-1){
					fos.write(b, 0, length);
				}
				fos.flush();
				 //通过如下代码，把文件保存为jpg格式
				BufferedImage img = ImageUtil.change2jpg(file);
				ImageIO.write(img, "jpg", file);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "@admin_category_list";
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		List<Category> cs = categoryDao.list(page.getStart(), page.getCount());
		int total = categoryDao.getTotal();
		System.out.println("total:"+total);
		page.setTotal(total);
		request.setAttribute("thecs", cs);
		request.setAttribute("page", page);
		return "admin/listCategory.jsp";
	}

}

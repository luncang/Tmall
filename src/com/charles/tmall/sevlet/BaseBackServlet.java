package com.charles.tmall.sevlet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.charles.tmall.dao.CategoryDao;
import com.charles.tmall.dao.OrderDao;
import com.charles.tmall.dao.OrderItemDao;
import com.charles.tmall.dao.ProductDao;
import com.charles.tmall.dao.ProductImageDao;
import com.charles.tmall.dao.PropertyDao;
import com.charles.tmall.dao.PropertyValueDao;
import com.charles.tmall.dao.ReviewDao;
import com.charles.tmall.dao.UserDao;
import com.charles.tmall.sevlet.interfaces.IBaseTask;
import com.charles.tmall.utils.Page;


/**
 * Servlet implementation class BaseBackServlet
 */
@WebServlet("/BaseBackServlet")
public abstract class BaseBackServlet extends HttpServlet implements IBaseTask {
	private static final long serialVersionUID = 1L;
	protected CategoryDao categoryDao = new CategoryDao();
	protected OrderDao orderDao = new OrderDao();
	protected OrderItemDao orderItemDao = new OrderItemDao();
	protected ProductDao productDao = new ProductDao();
	protected ProductImageDao productImageDao = new ProductImageDao();
	protected PropertyDao propertyDao = new PropertyDao();
	protected PropertyValueDao propertyValueDao = new PropertyValueDao();
	protected ReviewDao reviewDao = new ReviewDao();
	protected UserDao userDao = new UserDao();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BaseBackServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doget:");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("dopost:");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("service:");

		/* 获取分页信息 */
		int start = 0;
		int count = 5;
		try {
			if (request.getParameter("page.start") != null && request.getParameter("page.count") != null){
				start = Integer.parseInt(request.getParameter("page.start"));
				count = Integer.parseInt(request.getParameter("page.count"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Page page = new Page(start, count);
		/* 借助反射，调用对应的方法 */
		String method = request.getParameter("method");
		method = (String) request.getSession().getAttribute("method");
		System.out.println("method:"+method);
		String redirect = "";
		Method m;
		try {
			m = this.getClass().getMethod(method, HttpServletRequest.class, HttpServletResponse.class, Page.class);
			redirect = m.invoke(this, request, response, page).toString();
			System.out.println("redirect:"+redirect);
			/* 根据方法的返回值，进行相应的客户端跳转，服务端跳转，或者仅仅是输出字符串 */

			if (redirect.startsWith("@")) {
				response.sendRedirect(redirect.substring(1));
			} else if (redirect.startsWith("%")) {
				response.getWriter().println(redirect.substring(1));
			} else {
				request.getRequestDispatcher(redirect).forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * 获取输入流
	 * 
	 * @param request
	 * @param params
	 * @return
	 */
	public InputStream parseUpload(HttpServletRequest request, Map<String, String> params) {
		InputStream is = null;
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 設置上传文件的大小限制为10M
			factory.setSizeThreshold(10 * 1024 * 1024);
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					// 上传文件输入项
					// item.getInputStream()获取上传文件输入流
					is = item.getInputStream();
				} else {
					// 普通输入项
					String paramName = item.getFieldName();
					String paramValue = item.getString();
					paramValue = new String(paramValue.getBytes("ISO-8859-1"), "UTF-8");
					params.put(paramName, paramValue);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;
	}

}

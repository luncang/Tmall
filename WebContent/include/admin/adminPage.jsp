<!-- 模仿天猫整站j2ee 教程 为how2j.cn 版权所有-->
<!-- 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关-->
<!-- 供购买者学习，请勿私自传播，否则自行承担相关法律责任-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>


<script>
	$(function() {
		$("ul.pagination li.disabled a").click(function() {
			return false;
		});
	});
</script>

<nav>
	<ul class="pagination">
		<li <c:if test="${!page.hasPrevious}">class="disabled"</c:if>><a
			href="?page.start=0${page.param }" aria-label="Previous"> <span
				aria-hidden="true">首页</span>
		</a></li>

		<li <c:if test="${!page.hasPrevious }">class="disabled"</c:if>><a
			href="?page.start=${page.start-page.count}${page.param }"
			aria-label="Previous"> <span aria-hidden="true">上一页</span>
		</a></li>
		<c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">

							<!-- 刚才又去调试了一下代码才回忆起来。
				比如一共有500条数据，每页显示5个数据，那么一共有100页。
				如果没有这段代码，那么就会显示1-100个页面链接在这里，看上去就很长。
				有了这段代码，那么就只会显示当前页面左边3个，右边3个。 比如当前页面是10的话，那么就只会显示：
				7 8 9   10   11 12 13
				这样不会把1-100全部显示出来。 -->
			<c:if
				test="${status.count*page.count-page.start<=20 && status.count*page.count-page.start>=-10}">
				<li
					<c:if test="${status.index*page.count==page.start}">class="disabled"</c:if>>
					<a href="?page.start=${status.index*page.count}${page.param}"
					<c:if test="${status.index*page.count==page.start}">class="current"</c:if>>${status.count}</a>
				</li>
			</c:if>
		</c:forEach>

		<li <c:if test="${!page.hasNext }">class="disabled"</c:if>><a
			href="?page.start=${page.start+page.count}${page.param }"
			aria-label="Next"> <span aria-hidden="true">下一页</span>
		</a></li>
		<li <c:if test="${!page.hasNext }">class="disabled"</c:if>><a
			href="?page.start=${page.last}${page.param }" aria-label="Next">
				<span aria-hidden="true">尾页</span>
		</a></li>
	</ul>
</nav>

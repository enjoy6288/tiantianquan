<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/tr/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK rel="stylesheet" type="text/css"
	href="${baseurl}js/easyui/styles/default.css">
<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>

<script type="text/javascript">
	$(function() {
		//修改之前的值
		var goingTo = '${product.goingTo}';
		if(0==goingTo){
			$('#goingTo').text('商品页');
		}
		if(1==goingTo){
			$('#goingTo').text('网页');
		}

	});
</script>
</head>
<body>


	<form id="productForm" action="${baseurl}product/saveProduct.action"
		method="post" enctype="multipart/form-data">
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%"
			bgColor=#c4d8ed>

			<TBODY>
				<tr>
					<td>
						<TABLE class="toptable grid" border=1 cellSpacing=1 cellPadding=4
							align=center>
							<TBODY>
								<tr>
									<td height=30 width="10%" align=right> 跳转至：</td>
									<td class=category width="90%" id="goingTo">
									</td>
								</tr>
								<tr>
									<td height=30 width="10%" align=right>链接：</td>
									<td class=category width="90%">
										${product.linkUrl}
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right>淘宝ID：</td>
									<td class=category width="90%">
										${product.taobaoId}
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right>商品标题：</td>
									<td class=category width="90%">
										${product.title}
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right>商品描述：</td>
									<td class=category width="90%">
										${product.productDesc}
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right>商品图片：</td>
									<td class=category width="90%">
										<img style="width:200px;height:200px;" src="${product.img}">
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right>商品现价：</td>
									<td class=category width="90%">
										${product.priceNow}
									</td>
								</tr>
								<tr>
									<td height=30 width="10%" align=right>商品原价：</td>
									<td class=category width="90%">
										${product.priceOld}
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right>收藏人数：</td>
									<td class=category width="90%">
										${product.collectManual}
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right>上架时间：</td>
									<td class=category width="90%">
										${product.shelvesTime}
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right>排序值：</td>
									<td class=category width="90%">
										${product.sortValue}
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right>商品类别：</td>
									<td class=category width="90%" id="categoryId" name="categoryId">
										${product.categoryName}
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right>备注：</td>
									<td class=category width="90%">
											${product.remarks}
									</td>
								</tr>
							</TBODY>
						</TABLE>
					</td>
				</tr>
			</TBODY>
		</TABLE>
	</form>
</body>
</html>
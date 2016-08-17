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
<title>添加用户</title>

<script type="text/javascript">
	function saveProduct() {
		var formObj = jQuery("#productForm");
		var options = {
			dataType : "json",
			success : function(data) {
				saveProduct_callback(data);
			}
		};
		formObj.ajaxSubmit(options);
	}

	//ajax调用的回调函数，ajax请求完成调用此函数，传入的参数是action返回的结果
	function saveProduct_callback(data) {
		//结果提示信息
		message_alert(data);
		var type = data.resultInfo.type;
		if (type == 1) {
			window.location = '${baseurl}product/queryProductView.action?status=1';
		}

	}
	
	$(function() {
		$.formValidator.initConfig({formID:"productForm",debug:false,submitOnce:true,
			onSuccess:function(msg,obj,errorlist){
			}
		});
		$("#linkUrl").formValidator({onShow:"请填写",onCorrect:"正确  "}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空符号"},onError:"不能为空"});
		$("#taobaoId").formValidator({onShow:"请填写",onCorrect:"正确  "}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空符号"},onError:"不能为空"});
		$("#title").formValidator({onShow:"请填写",onCorrect:"正确  "}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空符号"},onError:"不能为空"});
		$("#productDesc").formValidator({onShow:"请填写",onCorrect:"正确  "}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空符号"},onError:"不能为空"});
		$("#img").formValidator({onShow:"请填写",onCorrect:"正确  "}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空符号"},onError:"不能为空"});
		$("#priceNow").formValidator({onShow:"请填写",onCorrect:"正确  "}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空符号"},onError:"不能为空"});
		$("#collectManual").formValidator({onShow:"请填写",onCorrect:"正确  "}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空符号"},onError:"不能为空"});
		$("#shelvesTime").formValidator({onShow:"请填写",onCorrect:"正确  "}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空符号"},onError:"不能为空"});
		$("#sortValue").formValidator({onShow:"请填写",onCorrect:"正确  "}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空符号"},onError:"不能为空"});
	});
</script>
</head>
<body>


	<form id="productForm" action="${baseurl}product/saveProduct.action"
		method="post" enctype="multipart/form-data">
		<!-- 该字段用于专题添加商品接受专题ID参数 -->
		<input type="hidden" name="topicId" value="${topicId}" />
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%"
			bgColor=#c4d8ed>
			<TBODY>
				<tr>
					<td>
						<TABLE class="toptable grid" border=1 cellSpacing=1 cellPadding=4
							align=center>
							<TBODY>
								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 跳转至：</td>
									<td class=category width="90%">
										<div>
											<select id="goingTo" name="goingTo" style="width: 300px;">
												<option value="0">商品页</option>
												<option value="1">网页</option>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 链接：</td>
									<td class=category width="90%">
										<div>
											<input id="linkUrl" name="linkUrl" style="width: 800px;"
												type="text"
												value="https://detail.tmall.com/item.htm?id=527471202371&scene=taobao_shop&ali_trackid=17_77d6befaede9594e73339d656ccb4793&spm=a21bo.50862.201863-6.2.RVurn6" />
										</div>
										<div id="linkUrlTip"></div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 淘宝ID：</td>
									<td class=category width="90%">
										<div>
											<input id="taobaoId" name="taobaoId" style="width: 800px;"
												type="text" value="enjoy6288" />
										</div>
										<div id="taobaoIdTip"></div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 商品标题：</td>
									<td class=category width="90%">
										<div>
											<input id="title" name="title" style="width: 800px;"
												type="text" value="马可维奇鞋子男潮鞋板鞋运动鞋2016夏季透气新款休闲鞋真皮" />
										</div>
										<div id="titleTip"></div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 商品描述：</td>
									<td class=category width="90%">
										<div>
											<input id="productDesc" name="productDesc"
												style="width: 800px;" type="text"
												value="高端大气上档次。暑假里要出去旅游，得选择一双合脚舒适的鞋，当然鞋子漂亮帅气也是必不可少的。我轻车熟路地点进马可维奇店，几乎没作什么犹豫就选中这款并下了单，新来的那个小同事见了，大惊小怪地叫道：你怎这么快就下单了这个“马可波罗”了？不看不比不还价了？抱歉，跟他说过几次了，这小子还是把马可维奇叫成马可波罗。我笑他少见多怪，告诉他我的选择错不了。今天这小子也看了鞋，果然信了。" />
										</div>
										<div id="productDescTip"></div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 商品图片：</td>
									<td class=category width="90%">
										<div>
											<input id="img" type="file" name="img" />
										</div>
										<div id="imgTip"></div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 商品现价：</td>
									<td class=category width="90%">
										<div>
											<input id="priceNow" name="priceNow" style="width: 800px;"
												type="text" value="10" />
										</div>
										<div id="priceNowTip"></div>
									</td>
								</tr>
								<tr>
									<td height=30 width="10%" align=right>商品原价：</td>
									<td class=category width="90%">
										<div>
											<input id="priceOld" name="priceOld" style="width: 800px;"
												type="text" value="150" />
										</div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 收藏人数：</td>
									<td class=category width="90%">
										<div>
											<input id="collectManual" name="collectManual"
												style="width: 800px;" type="text" value="2000" />
										</div>
										<div id="collectManualTip"></div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 上架时间：</td>
									<td class=category width="90%">
										<div>
											<INPUT type="text" id="shelvesTime" name="shelvesTime"
												onfocus="WdatePicker({isShowWeek:false,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
												style="width: 180px" />
										</div>
										<div id="shelvesTimeTip"></div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 排序值：</td>
									<td class=category width="90%">
										<div>
											<input id="sortValue" name="sortValue" style="width: 800px;"
												type="text" value="99" />
										</div>
										<div id="sortValueTip"></div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right>商品类别：</td>
									<td class=category width="90%" align=left><select
										id="categoryId" name="categoryId" style="width: 150px">
											<option value="">全部</option>
											<c:forEach items="${categorys}" var="value">
												<option value="${value.id}">${value.categoryName}</option>
											</c:forEach>
									</select></td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right>备注：</td>
									<td class=category width="90%">
										<div>
											<textarea id="remarks" name="remarks" rows="3" cols="20"
												style="width: 600px;"></textarea>
										</div>
									</td>
								</tr>

								<tr>
									<td colspan=2 align=center class=category><a
										id="submitbtn" class="easyui-linkbutton" iconCls="icon-ok"
										href="#" onclick="saveProduct()">提交</a></td>
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
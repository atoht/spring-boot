<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
<div id="add-user-modal" title="添加用户" >
  <form id="add-user-form" action="testPost" method="post">
    <table class="modal-tbl">
      <tr><td>ID</td><td><input type="text" name="id"></td></tr>
      <tr><td>账号</td><td><input type="text" name="name"></td></tr>
    </table>
    <button class="btn btn-lg btn-primary btn-block" type="button" onclick="listAllUser()">登录</button>
  </form>
</div>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.serializeJSON/2.9.0/jquery.serializejson.min.js"></script>
<script type="text/javascript">
$(function() {
// 	listAllUser();
});
	 
function listAllUser() {
	var id = 2;
	var name = "dfgd";
	
	// 获取序列化表单信息
	var user = $("#add-user-form").serializeJSON();
	var testForm = JSON.stringify(user);
	console.log(testForm);
	$.ajax({
    	type: "POST",
    	data: testForm,
  		url: "testPost",
  		contentType:"application/json",//这里设置很重要
  	    dataType: "json",
  		success: function(data) {
  		}
	});
}
</script>
</body>
</html>
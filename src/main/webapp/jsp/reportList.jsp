<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1><s:text name="section.report.pending"/></h1>
<p></p>
<s:if test="objectBlock.getList().isEmpty()">
	<p><s:text name="messages.isempty"/></p>
</s:if>
<s:else>
	<table class="table table-striped table-bordered table-condensed">
		<tr>
	        <th><s:text name="report.user"/></th>
	        <th><s:text name="report.link"/></th>
	        <th><s:text name="report.reason"/></th>
	        <th><s:text name="report.submited"/></th>
	        <th></th>
	    </tr>
	    <s:iterator value="objectBlock.list">
	        <tr>
	        	<s:url action="user_info" id="loginurl">
					<s:param name="login" value="user.login" />
				</s:url>
	            <td><s:a href="%{loginurl}"><s:property value="user.login"/></s:a></td>
				<td><s:a href="/link/%{link.ftitle}/"><s:property value="link.ftitle"/></s:a></td>
				<td><s:property value="reason"/></td>
	            <td><s:date name="submited"/></td>
	            <s:url action="report_check" id="reporturl">
	                <s:param name="id" value="reportId" />
	            </s:url>
	            <td><s:a href="%{reporturl}"><s:text name="report.check"/></s:a></td>
	        </tr>
	    </s:iterator>
	</table>
</s:else>
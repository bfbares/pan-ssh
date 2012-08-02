<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<h1><s:text name="section.error"/></h1>
<p class="lead"><s:text name="er.text"/></p>
<s:if test="#attr['exception'] != null">
	<div class="well">
		<p><s:text name="er.exname"/>: <s:property value="exception" /></p>
		<p><s:text name="er.exstack"/>: <s:property value="exceptionStack" /></p>
	</div>
</s:if>
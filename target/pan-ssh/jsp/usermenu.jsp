<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="#session['user'] != null">
	<ul class="nav pull-right">
	<li class="divider-vertical"></li>
	<li><s:a href="/profileForm"><s:text name="section.profile"/></s:a></li>
	<li class="divider-vertical"></li>
	<li><s:a href="/logout"><s:text name="section.logout"/></s:a></li>
	</ul>
</s:if>
<s:else>
	<ul class="nav pull-right">
	<li class="divider-vertical"></li>
	<li><s:a href="/loginForm"><s:text name="section.login"/></s:a></li>
	<li class="divider-vertical"></li>
	<li><s:a href="/register"><s:text name="section.register"/></s:a></li>
	</ul>
</s:else>
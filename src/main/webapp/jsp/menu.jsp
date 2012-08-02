<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<ul class="nav">
	<li><a href="/index"><s:text name="section.frontpage"/></a></li>
	<s:if test="%{(#session['user'].level.toString() == \"ADMIN\") || 
	              (#session['user'].level.toString() == \"GOD\")}">
		<li class="dropdown">
			<a href="#"
			      class="dropdown-toggle"
			      data-toggle="dropdown">
			      <s:text name="section.admin"/>
			      <b class="caret"></b>
			</a>
			<ul class="dropdown-menu">
			  <li><a href="/secure/user_list"><s:text name="section.listall"/></a></li>
			</ul>
		</li>
  	</s:if>
  	<s:if test="%{(#session['user'].level.toString() == \"GOD\")}">
		<li class="dropdown">
			<a href="#"
			      class="dropdown-toggle"
			      data-toggle="dropdown">
			      <s:text name="section.god"/>
			      <b class="caret"></b>
			</a>
			<ul class="dropdown-menu">
			  <li><a href="/categoryForm"><s:text name="section.categoryAdd"/></a></li>
			  <li><a href="/category_list"><s:text name="section.listcategory"/></a></li>
			</ul>
		</li>
  	</s:if>
</ul>
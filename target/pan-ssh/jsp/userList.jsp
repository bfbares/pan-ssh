<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1><s:text name="section.listall"/></h1>
<p></p>
<table class="table table-striped table-bordered table-condensed">
	<tr>
        <th><s:a href="/user_list/login/"><s:text name="user.login"/></s:a></th>
        <th><s:a href="/user_list/email/"><s:text name="user.email"/></s:a></th>
        <th><s:a href="/user_list/created/"><s:text name="user.created"/></s:a></th>
        <th><s:a href="/user_list/lastlogin/"><s:text name="user.lastlogin"/></s:a></th>
        <th><s:a href="/user_list/karma/"><s:text name="user.karma"/></s:a></th>
        <th><s:a href="/user_list/level/"><s:text name="user.level"/></s:a></th>
        <th><s:a href="/user_list/ip/"><s:text name="user.ip"/></s:a></th>
    </tr>
    <s:iterator value="objectBlock.list">
        <tr>
            <td><s:a href="/user/%{login}"><s:property value="login"/></s:a></td>
            <td><a href="<s:url value="mailto:%{email}" />" target="_blank"><s:property value="email"/></a></td>
            <td><s:date name="created"/></td>
            <td><s:date name="lastlogin"/></td>
            <td><s:property value="karma"/></td>
            <td><s:property value="level"/></td>
            <td><s:property value="ip"/></td>
        </tr>
    </s:iterator>
</table>
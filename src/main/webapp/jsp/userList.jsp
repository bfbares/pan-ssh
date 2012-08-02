<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1><s:text name="section.listall"/></h1>
<p></p>
<table class="table table-striped table-bordered table-condensed">
	<tr>
        <th><s:text name="user.login"/></th>
        <th><s:text name="user.email"/></th>
        <th><s:text name="user.created"/></th>
        <th><s:text name="user.lastlogin"/></th>
        <th><s:text name="user.karma"/></th>
        <th><s:text name="user.level"/></th>
        <th><s:text name="user.ip"/></th>
    </tr>
    <s:iterator value="objectBlock.list">
        <tr>
            <td><s:property value="login"/></td>
            <td><s:property value="email"/></td>
            <td><s:date name="created"/></td>
            <td><s:date name="lastlogin"/></td>
            <td><s:property value="karma"/></td>
            <td><s:property value="level"/></td>
            <td><s:property value="ip"/></td>
        </tr>
    </s:iterator>
</table>
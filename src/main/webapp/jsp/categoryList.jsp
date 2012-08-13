<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1><s:text name="section.listcategory"/></h1>
<p></p>
<table class="table table-striped table-bordered table-condensed">
	<tr>
        <th><s:text name="category.categoryId"/></th>
        <th><s:text name="category.name"/></th>
        <th><s:text name="category.parent"/></th>
        <th></th>
    </tr>
    <s:iterator value="list">
        <tr>
            <td><s:property value="categoryId"/></td>
            <td><s:property value="name"/></td>
            <td><s:property value="parent.name"/></td>
            <td>
                <s:url action="categoryForm" id="url">
                    <s:param name="id" value="categoryId" />
                </s:url>
                <s:a href="%{url}"><s:text name="form.edit"/></s:a> - 
                <s:url action="category_delete" id="url">
                    <s:param name="id" value="categoryId" />
                </s:url>
                <s:a href="%{url}"><s:text name="form.delete"/></s:a>
            </td>
        </tr>
    </s:iterator>
</table>
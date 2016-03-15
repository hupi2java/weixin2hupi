<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<meta name=”viewport” content=”width=device-width, initial-scale=1″ />
<html>
  <head>
    <title>weatherInfo</title> 
  </head>
  
  <body>

    <table>
    	<tr>
    		<td>${today.date }</td>
    		<td>${today.week }</td>
    		<td>${today.type }</td>
    		<td>${today.fengli }</td>
    		<td>${today.lowtemp }~${today.hightemp }</td>
    	</tr>
		<s:iterator value="#forecast">
    	<tr>
    		<td>${date }</td>
    		<td>${week }</td>
    		<td>${type }</td>
    		<td>${fengli }</td>
    		<td>${lowtemp }~${hightemp }</td>
    	</tr>
    	</s:iterator>
    </table>
    
    <table>
    	<s:iterator value="#weatherInfo">
    		<tr>
    			<td>${name }:【${index }】${details }</td>
    		</tr>
    	</s:iterator>
    </table>
  </body>
</html>

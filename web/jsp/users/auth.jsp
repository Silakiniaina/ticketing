<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="mg.dash.mvc.helper.ErrorHelper" %>
<%
    HashMap<String, String> errors = (HashMap<String, String>) request.getAttribute("validationErrors");
%>
    <!DOCTYPE html>
    <html :class="{ 'theme-dark': dark }" x-data="data()" lang="en">

    <head>
        <%@ include file="/WEB-INF/views/partials/head.jsp" %>
    </head>

    <body>
        <div class="flex items-center min-h-screen p-6 bg-gray-50 dark:bg-gray-900">
            <div class="flex-1 h-full max-w-4xl mx-auto overflow-hidden bg-white rounded-lg shadow-xl dark:bg-gray-800">
                <div class="flex flex-col overflow-y-auto md:flex-row">
                    <div class="h-32 md:h-auto md:w-1/2">
                        <img aria-hidden="true" class="object-cover w-full h-full dark:hidden"
                            src="${pageContext.request.contextPath}/assets/img/login-office.jpeg" alt="Office" />
                        <img aria-hidden="true" class="hidden object-cover w-full h-full dark:block"
                            src="${pageContext.request.contextPath}/assets/img/login-office-dark.jpeg" alt="Office" />
                    </div>
                    <div class="flex items-center justify-center p-6 sm:p-12 md:w-1/2">
                        <form action="" method="post" class="w-full">
                            <h1 class="mb-4 text-xl font-semibold text-gray-700 dark:text-gray-200">
                                Login
                            </h1>
                            <label class="block text-sm">
                                <span class="text-gray-700 dark:text-gray-400">Email</span>
                                <input
                                    type="email"
                                    name="user.email"
                                    value="<%= ErrorHelper.getOldValue(request, "user.email") %>"
                                    class="<%= ErrorHelper.getError(errors, "email") != null ? "error" : "" %> block w-full mt-1 text-sm dark:border-gray-600 dark:bg-gray-700 focus:border-purple-400 focus:outline-none focus:shadow-outline-purple dark:text-gray-300 dark:focus:shadow-outline-gray form-input"
                                    placeholder="Jane Doe"
                                />
                                <% if (ErrorHelper.getError(errors, "email") != null) { %>
                                    <div class="error-message"><%= ErrorHelper.getError(errors, "email") %></div>
                                <% } %>
                            </label>
                            <label class="block mt-4 text-sm">
                                <span class="text-gray-700 dark:text-gray-400">Password</span>
                                <input
                                    class="<%= ErrorHelper.getError(errors, "password") != null ? "error" : "" %> block w-full mt-1 text-sm dark:border-gray-600 dark:bg-gray-700 focus:border-purple-400 focus:outline-none focus:shadow-outline-purple dark:text-gray-300 dark:focus:shadow-outline-gray form-input"
                                    placeholder="***************"
                                    type="password"
                                    name="user.password"
                                    value="<%= ErrorHelper.getOldValue(request, "user.password") %>"
                                />
                                <% if (ErrorHelper.getError(errors, "password") != null) { %>
                                    <div class="error-message"><%= ErrorHelper.getError(errors, "password") %></div>
                                <% } %>
                            </label>

                            <!-- You should use a button here, as the anchor is only used for the example  -->
                            <button type="submit"
                                class="block w-full px-4 py-2 mt-4 text-sm font-medium leading-5 text-center text-white transition-colors duration-150 bg-purple-600 border border-transparent rounded-lg active:bg-purple-600 hover:bg-purple-700 focus:outline-none focus:shadow-outline-purple"
                                href="/auth">
                                Log in
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="/WEB-INF/views/partials/scripts.jsp" %>
    </body>

    </html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html :class="{ 'theme-dark': dark }" x-data="data()" lang="en">

<head>
    <%@ include file="/WEB-INF/views/partials/head.jsp" %>
</head>

<body>
    <div class="flex h-screen bg-gray-50 dark:bg-gray-900" :class="{ 'overflow-hidden': isSideMenuOpen}">
        <%@ include file="/WEB-INF/views/partials/sidebar.jsp" %>
            <div class="flex flex-col flex-1">
                <%@ include file="/WEB-INF/views/partials/navbar.jsp" %>
                    <main class="h-full pb-16 overflow-y-auto">
                        <div class="container px-6 mx-auto grid">
                            <h2 class="my-6 text-2xl font-semibold text-gray-700 dark:text-gray-200">
                                My Content
                            </h2>
                            <!-- Add your page-specific content here -->
                        </div>
                    </main>
            </div>
    </div>
    <%@ include file="/WEB-INF/views/partials/scripts.jsp" %>
</body>

</html>
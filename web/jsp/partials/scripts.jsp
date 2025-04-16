<script src="https://cdn.jsdelivr.net/gh/alpinejs/alpine@v2.x.x/dist/alpine.min.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/js/init-alpine.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/js/charts-lines.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/js/charts-pie.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/js/focus-trap.js" defer></script>
<script>
    function data() {
        return {
            isSideMenuOpen: false,
            isPagesMenuOpen: false,
            isNotificationsMenuOpen: false,
            isProfileMenuOpen: false,
            dark: localStorage.getItem('dark') === 'true',
            toggleSideMenu() {
                this.isSideMenuOpen = !this.isSideMenuOpen;
            },
            closeSideMenu() {
                this.isSideMenuOpen = false;
            },
            togglePagesMenu() {
                this.isPagesMenuOpen = !this.isPagesMenuOpen;
            },
            toggleNotificationsMenu() {
                this.isNotificationsMenuOpen = !this.isNotificationsMenuOpen;
            },
            closeNotificationsMenu() {
                this.isNotificationsMenuOpen = false;
            },
            toggleProfileMenu() {
                this.isProfileMenuOpen = !this.isProfileMenuOpen;
            },
            closeProfileMenu() {
                this.isProfileMenuOpen = false;
            },
            toggleTheme() {
                this.dark = !this.dark;
                localStorage.setItem('dark', this.dark);
                document.documentElement.classList.toggle('theme-dark', this.dark);
            }
        };
    }
</script>
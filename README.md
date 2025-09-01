# Ticketing (POC)

**Ticketing** is a web-based **Proof of Concept (POC)** application built with the homemade Java MVC framework [Dash_MVC](https://github.com/Silakiniaina/Dash_MVC).  

It demonstrates how to build a simple **flight booking system** using a custom lightweight MVC framework.

---

## Functionalities

### User
- Login / authentication
- Multi-criteria flight search (destination, date, price, etc.)
- Flight booking

### Admin
- Login / authentication
- Manage flights (CRUD: create, read, update, delete)
- Configure flight booking settings
- Configure booking management options

---

## Requirements
- **Java 17+**
- **Git**
- **Servlet container** (Tomcat, Jetty, WildFly, etc.)

---

## Installation & Setup

### 1. Clone the repositories
```bash
# Ticketing application
git clone https://github.com/Silakiniaina/ticketing.git
cd ticketing

# Dash_MVC framework
git clone https://github.com/Silakiniaina/Dash_MVC.git
```

### 2. Build Dash_MVC
```bash
cd Dash_MVC
./build.sh
```
This generates the `Dash_MVC.jar`.

### 3. Copy the JAR into Ticketing
```bash
cp build/Dash_MVC.jar ../ticketing/lib/
```

### 4. Deploy Ticketing
1. Package Ticketing as a WAR (if not already structured as a deployable web app).  
2. Deploy it to your **Servlet container** (Tomcat, Jetty, or WildFly).  
3. Start the server.  

Then open in your browser:
```
http://localhost:8080/ticketing
```

---

## Project Structure
```
ticketing/
├── lib/                # Dash_MVC.jar goes here
├── src/                # Java source code (Controllers, Models, Views)
├── web/                # Web resources (JSP, HTML, CSS, JS)
├── WEB-INF/            # Web configuration (web.xml, etc.)
└── README.md
```

---

## Development Notes
- **Ticketing** is a **POC** project — not production-ready.
- To upgrade the framework, rebuild **Dash_MVC** and replace the JAR in `lib/`.
- Controllers map HTTP requests → Models handle logic → Views render responses.
- The project demonstrates how to implement both **user-facing features** and **admin panels** with Dash_MVC.

---

## Contributing
Contributions and feedback are welcome!  
You can fork this repository or help improve the [Dash_MVC](https://github.com/Silakiniaina/Dash_MVC) framework.

---

## License
This project is licensed under the MIT License.

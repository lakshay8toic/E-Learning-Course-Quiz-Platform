# 🚀 How to Run & Localhost on Chrome

This folder gives you **1-click shortcuts** and simple steps to run the EduPulse E-Learning platform locally and view it on Google Chrome.

---

## 🔗 Direct 1-Click Browser Link
Once the Java server is started, you can click this direct link anytime to open the website:

👉 **[Click Here to Open http://localhost:8080/](http://localhost:8080/)**

*(Or double-click [`Open_Platform.html`](./Open_Platform.html) in this folder — it will automatically redirect your browser to `http://localhost:8080/`)*.

---

## ⚡ Method 1: Automatic 1-Click Scripts (Recommended)

### On macOS / Linux:
Double-click or run from terminal:
```bash
./Run/start_mac_linux.sh
```
*What this does:*
1. Automatically compiles all Java files.
2. Starts the server.
3. Automatically launches **Google Chrome** to `http://localhost:8080/`.

---

### On Windows:
Double-click:
```cmd
Run\start_windows.bat
```
*What this does:*
1. Compiles the Java project.
2. Automatically opens Chrome/default browser to `http://localhost:8080/`.
3. Starts the server.

---

## 🛠️ Method 2: Manual Steps in VS Code Terminal

If you want to show your Sir step-by-step from the terminal:

### Step 1: Compile the Java Code
```bash
javac -d . *.java
```

### Step 2: Run the Main Class
```bash
java elearning.Main
```

### Step 3: Open in Chrome
Click or open:
👉 **[http://localhost:8080/](http://localhost:8080/)**

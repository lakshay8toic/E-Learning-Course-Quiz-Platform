package elearning;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WebServer {
    private HttpServer server;
    private PlatformManager pm;

    public WebServer(int port, PlatformManager pm) throws IOException {
        this.pm = pm;
        server = HttpServer.create(new InetSocketAddress(port), 0);

        // Simple routes
        server.createContext("/", exchange -> render(exchange, pageHome()));
        server.createContext("/courses", exchange -> render(exchange, pageCourses()));
        server.createContext("/quiz", exchange -> render(exchange, pageQuiz()));
        server.createContext("/submit", exchange -> handleSubmit(exchange));
        server.createContext("/results", exchange -> render(exchange, pageResults()));
    }

    public void start() {
        server.start();
        System.out.println(">>> Website Live at: http://localhost:8080/");
    }

    private void render(HttpExchange ex, String html) throws IOException {
        byte[] b = html.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        ex.sendResponseHeaders(200, b.length);
        ex.getResponseBody().write(b);
        ex.close();
    }

    // Shared Header
    private String header(String title) {
        return "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>" + title + "</title>"
             + "<style>"
             + "body { font-family: sans-serif; background: #f1f5f9; margin: 0; padding: 20px; color: #1e293b; }"
             + ".nav { background: #1e1b4b; padding: 15px 25px; border-radius: 8px; margin-bottom: 25px; }"
             + ".nav a { color: #e2e8f0; text-decoration: none; margin-right: 20px; font-weight: bold; }"
             + ".nav a:hover { color: #38bdf8; }"
             + ".card { background: white; padding: 20px; border-radius: 8px; margin-bottom: 15px; box-shadow: 0 2px 4px rgba(0,0,0,0.08); }"
             + ".btn { background: #4f46e5; color: white; padding: 8px 16px; text-decoration: none; border-radius: 6px; border: none; cursor: pointer; }"
             + ".btn:hover { background: #4338ca; }"
             + "</style></head><body>"
             + "<div class='nav'>"
             + "  <a href='/'>🏠 Home</a>"
             + "  <a href='/courses'>📚 Courses</a>"
             + "  <a href='/quiz'>📝 Live Quiz</a>"
             + "  <a href='/results'>🏆 Results</a>"
             + "</div><div style='max-width: 800px; margin: auto;'>";
    }

    private String footer() {
        return "<div style='text-align:center; margin-top:30px; font-size:0.85rem; color:#64748b; border-top:1px solid #cbd5e1; padding-top:10px;'>"
             + "<b>Project Team:</b> Lakshay [AP24110010677] (Team Lead) &bull; Md Ahab Firoz [AP24110011219]"
             + "</div></div></body></html>";
    }

    private String pageHome() {
        return header("EduPulse - Home")
             + "<div class='card'>"
             + "  <h2>🎓 Welcome to EduPulse E-Learning Platform</h2>"
             + "  <p>An interactive course and quiz system built entirely with Core Java concepts (Units 1 to 4).</p>"
             + "  <br><a href='/courses' class='btn'>Browse Courses</a> "
             + "  <a href='/quiz' class='btn' style='background:#0284c7;'>Take Assessment</a>"
             + "</div>"
             + "<div class='card'>"
             + "  <h3>Key Syllabus Topics:</h3>"
             + "  <ul>"
             + "    <li><b>Unit 1:</b> Abstract Class (<code>LearningItem</code>), Interface (<code>Displayable</code>), Inheritance (<code>super</code>, <code>this</code>, <code>static</code>)</li>"
             + "    <li><b>Unit 2:</b> Polymorphism (Overloading & Overriding), Custom Exception (<code>InvalidScoreException</code>)</li>"
             + "    <li><b>Unit 3:</b> Multithreading (<code>QuizTimerThread</code>) & Byte Streams (<code>FileOutputStream</code>/<code>FileInputStream</code>)</li>"
             + "    <li><b>Unit 4:</b> Collections (<code>HashMap</code>, <code>ArrayList</code>)</li>"
             + "  </ul>"
             + "</div>" + footer();
    }

    private String pageCourses() {
        StringBuilder sb = new StringBuilder(header("Courses"));
        sb.append("<h2>Available Courses</h2>");
        for (Course c : pm.courses.values()) {
            sb.append("<div class='card'>")
              .append("<h3>").append(c.getTitle()).append("</h3>")
              .append("<p><b>Instructor:</b> ").append(c.getInstructor()).append("</p>")
              .append("<p><b>Modules:</b> ").append(String.join(", ", c.getTopics())).append("</p>")
              .append("<button class='btn' onclick=\"alert('Enrolled in ").append(c.getTitle()).append("!')\">Enroll Free</button>")
              .append("</div>");
        }
        sb.append(footer());
        return sb.toString();
    }

    private String pageQuiz() {
        Quiz q = pm.quizzes.get(201);
        // Start background timer thread (Unit 3: Multithreading)
        new Thread(new QuizTimerThread(q.timeLimit)).start();

        StringBuilder sb = new StringBuilder(header("Assessment"));
        sb.append("<div class='card'>")
          .append("<div style='display:flex; justify-content:space-between; align-items:center;'>")
          .append("<h2>").append(q.getTitle()).append("</h2>")
          .append("<span style='background:#fee2e2; color:#b91c1c; padding:6px 12px; border-radius:6px; font-weight:bold;'>⏱ Time: <span id='t'>").append(q.timeLimit).append("</span>s</span>")
          .append("</div>")
          .append("<form method='POST' action='/submit' id='qForm'>")
          .append("<p style='margin:15px 0 5px 0;'><b>Your Name:</b></p>")
          .append("<input type='text' name='name' required placeholder='Enter student name' style='padding:8px; width:260px; margin-bottom:15px;'><br>");

        for (int i = 0; i < q.questions.size(); i++) {
            Question question = q.questions.get(i);
            sb.append("<div style='background:#f8fafc; padding:12px; border-radius:6px; margin-bottom:12px;'>")
              .append("<p><b>Q").append(i + 1).append(". ").append(question.text).append("</b></p>");
            for (int j = 0; j < question.options.length; j++) {
                sb.append("<label style='display:block; margin:4px 0;'><input type='radio' name='q").append(i)
                  .append("' value='").append(j).append("' required> ").append(question.options[j]).append("</label>");
            }
            sb.append("</div>");
        }

        sb.append("<button type='submit' class='btn'>Submit Test</button></form></div>")
          .append("<script>")
          .append("let s=").append(q.timeLimit).append("; setInterval(()=>{s--; document.getElementById('t').innerText=s; if(s<=0){alert('Time up!'); document.getElementById('qForm').submit();}},1000);")
          .append("</script>")
          .append(footer());
        return sb.toString();
    }

    private void handleSubmit(HttpExchange ex) throws IOException {
        Scanner sc = new Scanner(ex.getRequestBody(), StandardCharsets.UTF_8.name()).useDelimiter("\\A");
        String body = sc.hasNext() ? sc.next() : "";
        Map<String, String> map = new HashMap<>();
        for (String pair : body.split("&")) {
            String[] p = pair.split("=");
            if (p.length == 2) {
                map.put(URLDecoder.decode(p[0], StandardCharsets.UTF_8.name()),
                        URLDecoder.decode(p[1], StandardCharsets.UTF_8.name()));
            }
        }

        String name = map.getOrDefault("name", "Student");
        Quiz q = pm.quizzes.get(201);
        int score = 0;
        for (int i = 0; i < q.questions.size(); i++) {
            String ans = map.get("q" + i);
            if (ans != null && Integer.parseInt(ans) == q.questions.get(i).answer) {
                score++;
            }
        }

        // Unit 2: Exception handling - try, catch, finally
        try {
            pm.saveResult(name, score, q.questions.size());
        } catch (InvalidScoreException e) {
            System.out.println("Caught exception: " + e.getMessage());
        } finally {
            System.out.println("[finally block executed] Finished quiz evaluation for " + name);
        }

        String resHtml = header("Result")
            + "<div class='card' style='text-align:center; padding:30px;'>"
            + "  <h2>Assessment Completed! 🎉</h2>"
            + "  <p style='font-size:1.2rem; margin:15px 0;'>Student: <b>" + name + "</b></p>"
            + "  <p style='font-size:1.5rem; color:#4f46e5; font-weight:bold;'>Score: " + score + " / " + q.questions.size() + "</p>"
            + "  <p style='color:#64748b;'>Result saved to file using Java Byte Streams (FileOutputStream).</p>"
            + "  <br><a href='/quiz' class='btn'>Retake Quiz</a> <a href='/results' class='btn' style='background:#0284c7;'>View Leaderboard</a>"
            + "</div>" + footer();

        render(ex, resHtml);
    }

    private String pageResults() {
        StringBuilder sb = new StringBuilder(header("Results"));
        sb.append("<div class='card'><h2>🏆 Saved Quiz Records</h2>");
        List<String> list = pm.getResults(); // Unit 3 FileInputStream read
        if (list.isEmpty()) {
            sb.append("<p>No records found yet.</p>");
        } else {
            sb.append("<ol style='line-height:2;'>");
            for (String r : list) {
                sb.append("<li><b>").append(r).append("</b></li>");
            }
            sb.append("</ol>");
        }
        sb.append("</div>").append(footer());
        return sb.toString();
    }
}

package elearning;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WebServer {
    private final int port;
    private final PlatformManager manager;
    private HttpServer server;

    public WebServer(int port, PlatformManager manager) {
        this.port = port;
        this.manager = manager;
    }

    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

        // Routing handlers
        server.createContext("/", new HomeHandler());
        server.createContext("/courses", new CoursesHandler());
        server.createContext("/quizzes", new QuizzesHandler());
        server.createContext("/quiz", new TakeQuizHandler());
        server.createContext("/submit-quiz", new SubmitQuizHandler());
        server.createContext("/results", new ResultsHandler());

        server.setExecutor(null); // default executor
        server.start();
        System.out.println(">>> E-Learning Web Server running at: http://localhost:" + port + "/");
    }

    // Helper to send HTML response
    private static void sendResponse(HttpExchange exchange, int statusCode, String responseHtml) throws IOException {
        byte[] bytes = responseHtml.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    // Shared Header/Navbar
    private static String getHeader(String activeTab) {
        return "<!DOCTYPE html><html><head><meta charset='UTF-8'>" +
               "<title>EduPulse - E-Learning Course & Quiz Platform</title>" +
               "<link href='https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap' rel='stylesheet'>" +
               "<style>" +
               "* { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Inter', sans-serif; }" +
               "body { background: #f8fafc; color: #1e293b; line-height: 1.6; }" +
               ".navbar { background: #1e1b4b; color: white; padding: 1rem 2rem; display: flex; justify-content: space-between; align-items: center; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.1); }" +
               ".navbar h1 { font-size: 1.5rem; font-weight: 700; color: #a5b4fc; display: flex; align-items: center; gap: 8px; }" +
               ".nav-links a { color: #e0e7ff; text-decoration: none; margin-left: 1.5rem; font-weight: 500; font-size: 0.95rem; transition: color 0.2s; }" +
               ".nav-links a:hover, .nav-links a.active { color: #38bdf8; font-weight: 600; }" +
               ".container { max-width: 1050px; margin: 2rem auto; padding: 0 1rem; }" +
               ".card { background: white; border-radius: 12px; padding: 1.75rem; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); margin-bottom: 1.5rem; border: 1px solid #e2e8f0; }" +
               ".btn { display: inline-block; background: #4f46e5; color: white; padding: 0.6rem 1.2rem; border-radius: 8px; text-decoration: none; font-weight: 600; font-size: 0.9rem; border: none; cursor: pointer; transition: background 0.2s; }" +
               ".btn:hover { background: #4338ca; }" +
               ".btn-secondary { background: #0284c7; }" +
               ".badge { display: inline-block; padding: 0.25rem 0.6rem; border-radius: 9999px; font-size: 0.75rem; font-weight: 600; background: #e0e7ff; color: #4338ca; }" +
               ".grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(310px, 1fr)); gap: 1.5rem; }" +
               ".hero { background: linear-gradient(135deg, #312e81 0%, #4338ca 100%); color: white; border-radius: 16px; padding: 3rem 2rem; text-align: center; margin-bottom: 2rem; }" +
               ".hero h2 { font-size: 2.2rem; margin-bottom: 0.75rem; }" +
               ".hero p { font-size: 1.1rem; opacity: 0.9; max-width: 650px; margin: 0 auto 1.5rem auto; }" +
               ".footer { text-align: center; margin: 3rem 0 1.5rem 0; color: #64748b; font-size: 0.85rem; }" +
               "</style></head><body>" +
               "<nav class='navbar'>" +
               "  <h1><span>🎓</span> EduPulse Platform</h1>" +
               "  <div class='nav-links'>" +
               "    <a href='/' class='" + ("home".equals(activeTab) ? "active" : "") + "'>Home</a>" +
               "    <a href='/courses' class='" + ("courses".equals(activeTab) ? "active" : "") + "'>Courses</a>" +
               "    <a href='/quizzes' class='" + ("quizzes".equals(activeTab) ? "active" : "") + "'>Live Quizzes</a>" +
               "    <a href='/results' class='" + ("results".equals(activeTab) ? "active" : "") + "'>Leaderboard & History</a>" +
               "  </div>" +
               "</nav><div class='container'>";
    }

    private static String getFooter() {
        return "</div><div class='footer'>Java OOP & Web Project | Units 1-4 Demonstrated | Built for Academic Project Presentation</div></body></html>";
    }

    // 1. Home Handler
    private class HomeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            StringBuilder sb = new StringBuilder();
            sb.append(getHeader("home"));
            sb.append("<div class='hero'>");
            sb.append("<h2>Learn, Test & Excel with Interactive Quizzes</h2>");
            sb.append("<p>Explore computer science courses, attend assessments with simulated timer threads, and track verified results backed by Java Streams & OOP architecture.</p>");
            sb.append("<a href='/courses' class='btn' style='background:#38bdf8; color:#0f172a; margin-right: 10px;'>Browse Courses</a>");
            sb.append("<a href='/quizzes' class='btn' style='background:#f43f5e;'>Take a Quiz Now</a>");
            sb.append("</div>");

            sb.append("<h3 style='margin-bottom: 1rem; color: #1e1b4b;'>Syllabus Concepts Demonstrated</h3>");
            sb.append("<div class='grid'>");
            sb.append("<div class='card'><h4>Unit 1: Java & OOP</h4><p style='color:#64748b; font-size:0.9rem;'>Abstract class <code>LearningItem</code>, interface <code>Displayable</code>, inheritance (<code>Course</code> & <code>Quiz</code>), <code>super</code>, <code>this</code>, and <code>static</code>.</p></div>");
            sb.append("<div class='card'><h4>Unit 2: Polymorphism & Exceptions</h4><p style='color:#64748b; font-size:0.9rem;'>Constructor & Method Overloading, Method Overriding (Dynamic Dispatch), custom exception <code>InvalidScoreException</code> with try-catch-finally.</p></div>");
            sb.append("<div class='card'><h4>Unit 3: Threads & I/O</h4><p style='color:#64748b; font-size:0.9rem;'>Multithreading with <code>QuizTimerThread</code> (implements Runnable, synchronization), and persistent file logging via <code>FileOutputStream</code> & <code>FileInputStream</code>.</p></div>");
            sb.append("<div class='card'><h4>Unit 4: Collections</h4><p style='color:#64748b; font-size:0.9rem;'><code>HashMap</code> for courses and quizzes lookup, and <code>ArrayList</code> for questions and results.</p></div>");
            sb.append("</div>");

            sb.append(getFooter());
            sendResponse(exchange, 200, sb.toString());
        }
    }

    // 2. Courses Handler
    private class CoursesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            StringBuilder sb = new StringBuilder();
            sb.append(getHeader("courses"));
            sb.append("<div style='display:flex; justify-content:space-between; align-items:center; margin-bottom:1.5rem;'>");
            sb.append("<h2 style='color:#1e1b4b;'>Featured Courses</h2>");
            sb.append("<span class='badge' style='font-size:0.9rem;'>" + manager.getAllCourses().size() + " Available Courses</span>");
            sb.append("</div>");

            sb.append("<div class='grid'>");
            for (Course c : manager.getAllCourses()) {
                sb.append("<div class='card'>");
                sb.append("<span class='badge'>").append(c.getCategory()).append("</span>");
                sb.append("<h3 style='margin: 0.6rem 0; color:#1e1b4b;'>").append(c.getTitle()).append("</h3>");
                sb.append("<p style='font-size:0.85rem; color:#475569; margin-bottom:0.75rem;'><strong>Instructor:</strong> ").append(c.getInstructor()).append("</p>");
                sb.append("<p style='color:#64748b; font-size:0.9rem; margin-bottom:1rem;'>").append(c.getDescription()).append("</p>");
                
                sb.append("<h4 style='font-size:0.9rem; color:#334155; margin-bottom:0.5rem;'>Modules Covered:</h4>");
                sb.append("<ul style='font-size:0.85rem; color:#64748b; margin-left:1.2rem; margin-bottom:1.25rem;'>");
                for (String mod : c.getSyllabusModules()) {
                    sb.append("<li>").append(mod).append("</li>");
                }
                sb.append("</ul>");
                sb.append("<button class='btn' onclick=\"alert('Enrolled in " + c.getTitle() + " successfully!')\">Enroll Free</button>");
                sb.append("</div>");
            }
            sb.append("</div>");
            sb.append(getFooter());
            sendResponse(exchange, 200, sb.toString());
        }
    }

    // 3. Quizzes Handler
    private class QuizzesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            StringBuilder sb = new StringBuilder();
            sb.append(getHeader("quizzes"));
            sb.append("<h2 style='margin-bottom:1.5rem; color:#1e1b4b;'>Active Quizzes & Assessments</h2>");
            sb.append("<div class='grid'>");
            for (Quiz q : manager.getAllQuizzes()) {
                sb.append("<div class='card'>");
                sb.append("<span class='badge'>").append(q.getCategory()).append("</span>");
                sb.append("<h3 style='margin: 0.6rem 0;'>").append(q.getTitle()).append("</h3>");
                sb.append("<p style='color:#64748b; font-size:0.9rem; margin-bottom:1rem;'>Test your understanding in real time with our simulated exam environment.</p>");
                sb.append("<p style='font-size:0.85rem; color:#334155;'><strong>Questions:</strong> ").append(q.getQuestions().size()).append("</p>");
                sb.append("<p style='font-size:0.85rem; color:#334155; margin-bottom:1rem;'><strong>Time Limit:</strong> ").append(q.getTimeLimitSeconds()).append(" seconds (Managed via Thread)</p>");
                sb.append("<a href='/quiz?id=").append(q.getId()).append("' class='btn btn-secondary'>Start Quiz</a>");
                sb.append("</div>");
            }
            sb.append("</div>");
            sb.append(getFooter());
            sendResponse(exchange, 200, sb.toString());
        }
    }

    // 4. Take Quiz Handler
    private class TakeQuizHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery();
            int quizId = 201; // default
            if (query != null && query.contains("id=")) {
                try {
                    quizId = Integer.parseInt(query.split("id=")[1].split("&")[0]);
                } catch (NumberFormatException ignored) {}
            }

            Quiz quiz = manager.getQuiz(quizId);
            if (quiz == null) {
                sendResponse(exchange, 404, "Quiz not found");
                return;
            }

            // Start a multithreaded timer instance in background to showcase Unit 3 (Multithreading)
            QuizTimerThread timer = new QuizTimerThread(quiz.getTimeLimitSeconds());
            Thread t = new Thread(timer);
            t.start();

            StringBuilder sb = new StringBuilder();
            sb.append(getHeader("quizzes"));
            sb.append("<div class='card' style='border-top: 4px solid #4f46e5;'>");
            sb.append("<div style='display:flex; justify-content:space-between; align-items:center;'>");
            sb.append("<div><h2>").append(quiz.getTitle()).append("</h2><p style='color:#64748b;'>Answer all questions below and submit before the timer expires.</p></div>");
            sb.append("<div id='timer-box' style='background:#fee2e2; color:#b91c1c; padding:0.6rem 1.2rem; border-radius:8px; font-weight:700; font-size:1.1rem;'>⏱ Time: <span id='timer-sec'>").append(quiz.getTimeLimitSeconds()).append("</span>s</div>");
            sb.append("</div>");
            sb.append("<hr style='margin: 1.25rem 0; border:0; border-top:1px solid #e2e8f0;'>");

            sb.append("<form method='POST' action='/submit-quiz' id='quizForm'>");
            sb.append("<input type='hidden' name='quizId' value='").append(quiz.getId()).append("'>");
            
            sb.append("<div style='margin-bottom:1.5rem;'>");
            sb.append("<label style='font-weight:600; display:block; margin-bottom:0.4rem;'>Enter Your Name:</label>");
            sb.append("<input type='text' name='studentName' required placeholder='e.g., Rohit Sharma' style='padding:0.6rem 0.8rem; width:100%; max-width:380px; border:1px solid #cbd5e1; border-radius:6px; font-size:0.95rem;'>");
            sb.append("</div>");

            ArrayList<Question> questions = quiz.getQuestions();
            for (int i = 0; i < questions.size(); i++) {
                Question q = questions.get(i);
                sb.append("<div style='margin-bottom: 1.5rem; background:#f8fafc; padding:1.2rem; border-radius:8px;'>");
                sb.append("<p style='font-weight:600; font-size:1rem; margin-bottom:0.75rem; color:#1e293b;'>Q").append(i + 1).append(". ").append(q.getPrompt()).append("</p>");
                String[] opts = q.getOptions();
                for (int j = 0; j < opts.length; j++) {
                    sb.append("<label style='display:block; margin-bottom:0.4rem; cursor:pointer;'>");
                    sb.append("<input type='radio' name='q_").append(i).append("' value='").append(j).append("' required style='margin-right:8px;'>");
                    sb.append(opts[j]);
                    sb.append("</label>");
                }
                sb.append("</div>");
            }

            sb.append("<button type='submit' class='btn' style='font-size:1rem; padding:0.8rem 2rem;'>Submit Assessment</button>");
            sb.append("</form>");
            sb.append("</div>");

            // Timer script synced with the test
            sb.append("<script>");
            sb.append("let timeLeft = ").append(quiz.getTimeLimitSeconds()).append(";");
            sb.append("const timerElem = document.getElementById('timer-sec');");
            sb.append("const countdown = setInterval(() => {");
            sb.append("  timeLeft--;");
            sb.append("  timerElem.innerText = timeLeft;");
            sb.append("  if(timeLeft <= 0) {");
            sb.append("    clearInterval(countdown);");
            sb.append("    alert('Time is up! Submitting your answers automatically.');");
            sb.append("    document.getElementById('quizForm').submit();");
            sb.append("  }");
            sb.append("}, 1000);");
            sb.append("</script>");

            sb.append(getFooter());
            sendResponse(exchange, 200, sb.toString());
        }
    }

    // 5. Submit Quiz Handler
    private class SubmitQuizHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendResponse(exchange, 405, "Method Not Allowed");
                return;
            }

            // Read POST body
            Scanner scanner = new Scanner(exchange.getRequestBody(), StandardCharsets.UTF_8.name()).useDelimiter("\\A");
            String body = scanner.hasNext() ? scanner.next() : "";
            
            // Parse URL-encoded form data
            Map<String, String> params = new HashMap<>();
            for (String pair : body.split("&")) {
                String[] parts = pair.split("=");
                if (parts.length == 2) {
                    params.put(URLDecoder.decode(parts[0], StandardCharsets.UTF_8.name()),
                               URLDecoder.decode(parts[1], StandardCharsets.UTF_8.name()));
                }
            }

            int quizId = Integer.parseInt(params.getOrDefault("quizId", "201"));
            String studentName = params.getOrDefault("studentName", "Anonymous Student");
            Quiz quiz = manager.getQuiz(quizId);

            int score = 0;
            int total = quiz.getQuestions().size();

            for (int i = 0; i < total; i++) {
                String ans = params.get("q_" + i);
                if (ans != null) {
                    int selected = Integer.parseInt(ans);
                    if (quiz.getQuestions().get(i).checkAnswer(selected)) {
                        score++;
                    }
                }
            }

            // Unit 2: Exception handling demonstration
            try {
                manager.recordResult(studentName, quizId, score, total);
            } catch (InvalidScoreException e) {
                System.err.println("Exception occurred: " + e.getMessage());
            } finally {
                System.out.println("[Unit 2 finally block]: Result processing attempt finished for " + studentName);
            }

            double percentage = ((double) score / total) * 100.0;
            StringBuilder sb = new StringBuilder();
            sb.append(getHeader("quizzes"));
            sb.append("<div class='card' style='text-align:center; padding:3rem;'>");
            sb.append("<div style='font-size:3rem; margin-bottom:1rem;'>").append(percentage >= 50.0 ? "🎉" : "📚").append("</div>");
            sb.append("<h2>Quiz Completed, ").append(studentName).append("!</h2>");
            sb.append("<p style='font-size:1.1rem; color:#475569; margin: 1rem 0;'>You scored <strong>").append(score).append("</strong> out of <strong>").append(total).append("</strong> (").append(String.format("%.1f", percentage)).append("%)</p>");
            
            if (percentage >= 75.0) {
                sb.append("<div style='display:inline-block; background:#dcfce7; color:#15803d; padding:0.5rem 1rem; border-radius:999px; font-weight:600; margin-bottom:1.5rem;'>Status: Passed with Distinction 🏆</div><br>");
            } else if (percentage >= 50.0) {
                sb.append("<div style='display:inline-block; background:#fef9c3; color:#a16207; padding:0.5rem 1rem; border-radius:999px; font-weight:600; margin-bottom:1.5rem;'>Status: Passed 👍</div><br>");
            } else {
                sb.append("<div style='display:inline-block; background:#fee2e2; color:#b91c1c; padding:0.5rem 1rem; border-radius:999px; font-weight:600; margin-bottom:1.5rem;'>Status: Needs Improvement 📖</div><br>");
            }

            sb.append("<p style='font-size:0.85rem; color:#64748b; margin-bottom:2rem;'>Your result has been stored persistently in <code>quiz_results.txt</code> using Java Byte Streams.</p>");
            sb.append("<div>");
            sb.append("<a href='/quizzes' class='btn' style='margin-right:10px;'>Try Another Quiz</a>");
            sb.append("<a href='/results' class='btn btn-secondary'>View All Results</a>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append(getFooter());

            sendResponse(exchange, 200, sb.toString());
        }
    }

    // 6. Results & Leaderboard Handler
    private class ResultsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            StringBuilder sb = new StringBuilder();
            sb.append(getHeader("results"));
            sb.append("<div style='display:flex; justify-content:space-between; align-items:center; margin-bottom:1.5rem;'>");
            sb.append("<h2 style='color:#1e1b4b;'>Quiz Results & Submission History</h2>");
            sb.append("<span class='badge'>Persisted via Byte Streams (I/O)</span>");
            sb.append("</div>");

            List<String> results = manager.readResultsFromFile(); // Unit 3 File I/O
            sb.append("<div class='card'>");
            if (results.isEmpty()) {
                sb.append("<p style='color:#64748b; text-align:center;'>No quiz attempts recorded yet. Take a quiz to generate data!</p>");
            } else {
                sb.append("<table style='width:100%; border-collapse:collapse; font-size:0.95rem;'>");
                sb.append("<tr style='background:#f1f5f9; text-align:left; border-bottom:2px solid #cbd5e1;'>");
                sb.append("<th style='padding:0.75rem 1rem;'>#</th>");
                sb.append("<th style='padding:0.75rem 1rem;'>Submission Record</th>");
                sb.append("</tr>");
                int idx = 1;
                for (String res : results) {
                    sb.append("<tr style='border-bottom:1px solid #e2e8f0;'>");
                    sb.append("<td style='padding:0.75rem 1rem; color:#64748b;'>").append(idx++).append("</td>");
                    sb.append("<td style='padding:0.75rem 1rem; font-weight:500; color:#1e293b;'>").append(res).append("</td>");
                    sb.append("</tr>");
                }
                sb.append("</table>");
            }
            sb.append("</div>");
            sb.append(getFooter());
            sendResponse(exchange, 200, sb.toString());
        }
    }
}

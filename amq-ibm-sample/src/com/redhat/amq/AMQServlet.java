package com.redhat.amq;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.redhat.amq.jms.SenderAMQ;

@WebServlet(displayName = "AMQServlet", urlPatterns = "/AMQServlet")
public class AMQServlet extends HttpServlet {

    private static final long serialVersionUID = 6618728190618097169L;

    // @Inject
    private SenderAMQ senderAMQ = new SenderAMQ();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AMQServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @SuppressWarnings("resource")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // JMS data
        String connectionFactory = "amq/AMQConnectionFactory";
        if (null != request.getParameter("connectionFactory")) {
            connectionFactory = request.getParameter("connectionFactory");
        }
        String queue = "amq/queue/sampleQueue";
        if (null != request.getParameter("queue")) {
            queue = request.getParameter("queue");
        }
        String message = "Message to send";
        if (null != request.getParameter("message")) {
            message = request.getParameter("message");
        }

        Boolean result = this.senderAMQ.sendMessage(connectionFactory, queue, message);

        // HTML Result
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><head><title>Testing JMS-AMQ</title></head>");
        out.println("<body><h2>Test Result</h2>");
        if (result.booleanValue()) {
            out.println("<h4>Message sent!</h4>");
        } else {
            out.println("<h4>Messaged DID NOT send!</h4>");
        }
        out.println("</body></html>");
    }

}

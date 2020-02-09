# EPAM Java web development course - Online pharmacy app
- database scheme - [OnlinePharmacy.png](OnlinePharmacy.png)
- testing SQL queries examples and DB creation - [testsql](testsql)
# Application features
- User logs in or registres, after authorization, depending on the role, various operations are available.
- Administrator has access to all operations, including actions with a list of users, except of approving  prescriptions.
- Pharmacist (in the program - Manager) manages the list of drugs and has the same access as Administrator.
- Client(in the program - User) can browse and add the necessary drugs from the list of available ones to the shopping cart for the order.
- Some drugs require a prescription, which only a Doctor can prescribe to the Client.
- Client can go to the shopping cart and fill out the Order form, specify the quantity and request the necessary recipes.
- Client has access to a list of his orders and recipes.
- Manager and Administrator have access to all orders of all users.
- Client confirms the order, however, if the order contains drugs that require a prescription, he can create a request for a prescription, which is sent to the doctor for approval. At the same time, the order cannot be confirmed until the necessary recipes are approved.
- Customer pays for the order. Only the Client himself can pay for his order. Manager confirms receipt of payment.
- The prescription is valid for a limited time, if it has expired, the corresponding status is established.
- Client can make a request to the Doctor for an extension of the prescription term.
- After the Customer confirms the delivery, the Order is considered completed.
 # Application's layers
- View: [JSP pages](src/main/webapp/WEB-INF/jsp)
- Controller: [Servlet](src/main/java/by/epam/pavelshakhlovich/onlinepharmacy/controller/Controller.java)
- Model:
    - [command layer](src/main/java/by/epam/pavelshakhlovich/onlinepharmacy/command)  
    - [service layer](src/main/java/by/epam/pavelshakhlovich/onlinepharmacy/service)
    - [dao layer](src/main/java/by/epam/pavelshakhlovich/onlinepharmacy/dao)

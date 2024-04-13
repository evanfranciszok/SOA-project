# Introduction

# Motivation

* The motivation of your project, including the problem statement (the problem you aimed at solving) and your objectives.

# Business process

* The business process(es) that are supported by your solution.

# Architecture

* The architecture of your solution, which should be properly explained and motivated. In the architecture, you should describe the services that you defined and how they work together to solve the problem. Here you should also justify your choices of synchronous and asynchronous services, and message queues.

![component diagram](image\comonentDiagram.png)

# Design decisions

* The more concrete/specific design decisions, by explaining why you did (and didn't ) use different technologies such as SOAP, REST and Message queue. Consider giving a summary table listing the services with the information about why you used (or did not use) these technologies and communication styles.

Synchronous communication between Meal Planning Service and the Inventory Management Service. Both services are our own and don’t require intensive calculation or operations to complete. Therefore we will just use simple HTTP requests.

Synchronous communication between all services with the User Profile Service. We do this because both services will be completely run by us and therefore wouldn’t require any big processing. There will only be some querying done to get the required data in our own database.

Asynchronous communication between Shopping List Optimization and the Supermarket services (multiple) with a message queue. We don't want to be blocking our application to get the prices of the application from a third party (API of the supermarkets). In order to solve this we will use a message queue. This way we can just dump a lot of price requests and wait for the answer while we work with other information that we have received already or already have.

Currently we have an implementation that sends a message to the message queue when a price is requested. Each supermarket has its own message queue. A supermarket service listens to its queue and if there is a message with a price inquiry, it processes this request and sends back a message to a message queue with the productid, the price and the storename.

Asynchronous communication between Meal Planning Application and the Meal Planning Service with a websocket. This needs to be a websocket to have the most responsive experience for the client and will return results as quickly as possible even when there will be a waiting period by the generation of the recipes.

Currently we have an endpoint for the preferences of the user. This can be received with HTTP get requests. There is still just dummy data in there that can be retrieved.
There is also an UI for the application. From here the preference of the user can be read and edited. This is done by retrieving the data from the preference data. There is also a dummy presentation of the planning of the coming days from the meal planning services. This is currently just hardcoded but will be fixed to get the data from the meal planning service.
The Inventory service is currently not implemented but has a lot of similarities with the user preference service and can be partly reused.

# Validation

* Concrete evidence that you have validated your system, with testing and usage information.

# Relevant information

* Any other relevant information/knowledge that is necessary to appreciate your efforts in this project. The report should be complete and detailed enough so that the teachers don't need to look into the code to understand what has been done.

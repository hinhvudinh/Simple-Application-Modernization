# Simple Application Modernization using AWS Cloud Platform
Vietnamese Version: https://sdsrv.ai/blog/hien-dai-hoa-ung-dung-don-gian-su-dung-dam-may-dien-toan-aws/
## 1. Introduction
Though cloud technologies and modern programming models like cloud-native have been around for several years, most enterprise applications still run on-premise using traditional application servers. Despite of many benefits cloud technologies can bring, transforming such a traditional application to cloud environment with all their data and dependencies is not a simple task. In order to have basic understanding on application modernization process, this post introduces a simple scenario in which a company wants to refactor its Book Store application and migrate it from legacy environment (on-premise server) to AWS.  
## 2. Scenario
Company A uses a BookStore application to manage its books. Employees can get, update, create and delete books in the store. The application currently runs on old version of Java, which lacks the modern features and security enhancements as in the latest version. The company recognizes the need to upgrade the application to the latest Java version to ensure its security, stability and compatibility with modern systems. In addition, the application operates in on-premise server which has some limitations, such as hardware maintenance cost and traffic congestion issues whenever a hot book is introduced or a good sale campaign is executed.  
The company decides to re-factor the application to the latest Java version and migrate it to AWS for:    
- Optimizing the hardware cost  
- Reducing operational overhead  
- Improving application performance and availability  
## 3. Current status
The company engineer studies the BookStore application and report its current status as below:  

**Overview of the application**    
- *Main feature*: manage books in book store  

- *Functionality*:  
	- Get list of books  
	- Update the information of the books  
	- Create a new book when it comes to store  
	- Delete a book from the list  


*Business status*:  

![bookstore-asis-tobe](https://github.com/hinhvudinh/Simple-Application-Modernization-using-AWS-EKS/assets/18361892/aa1b6fb3-dcf7-4ec2-902f-f87e177e13eb)

              
The figure above represents the workflow of the BookStore application. Users has permission only to view the books while owner and developers can view, create, update and delete the books. The request then processes by application on local server and verify the availability based on BookId. If there is requested books in the list then the requesters can process the request, otherwise the application will return the error to the requesters. The application is required to have stability operation with 24/7 availability and near real-time demand. It also has to have the ability to handle operational overhead especially in the event of new books released or sale-off campaigns. However, it is difficult to scale the resources of the local sever to meet the demand while the owner need to purchase and install additional servers. It is critical pain points of the current architecture.


*Architecture status*:

![on-premises-diagram](https://github.com/hinhvudinh/Simple-Application-Modernization-using-AWS-EKS/assets/18361892/1b287104-a17d-4b18-94b6-d369c2ccd44d)


The BookStore application is currently organized as 3-tier architecture: the presentation tier, or user interface; the application tier, where data is processed; and the in memory data tier, where the data associated with the application is stored and managed. These layers are highly tight-coupling then it difficult to scale based on high traffic or if a tier change its logics, there may be errors on other tiers. In addition, it operates on old version of Java, which lacks the modern features and security enhancements as in the latest version.
## 4. Application modernization
Based on the study of the current status, the engineer wants to re-factor this application as follows:
Item | As-is | To-be
--- | --- | ---
Development language | Java | Java
Framework | JavaEE | Spring boot
API | Get, Put, Post, Delete | Get, Put, Post, Delete
DB | H2 in-memory DB | Amazon RDS MySQL

Traditionally, there are some problems related to JavaEE (Java Enterprise Edition) framework such as: using a lot of XML files, dependencies hard-coding, and the functions of JavaEE cannot be selected separately.
In contrast, SpringBoot, which was released in 2004, is a framework to create a program based on Java OOP (Object Oriented Programming) and has some advantages over JavaEE such as auto configuration and dependencies injection. The injection feature lets objects define their own dependencies which will be injected by framework container.  
For the issues of the on-premise server, the engineer suggests to migrate this application to AWS which has a lot of benefits likes:
- Reduce hardware up-front costs The owner only pays for the computer power, storage, and other resource that the application use, with no long-term contracts or up-front commitments.
- Easy to deploy and operate the application in a flexible way
- Fast to scale to deal with surges of traffic in occasional events
- Improve the application availability by utilizing multi-zone high availability setup in cloud infrastructure


For the architecture, the engineer decides to re-architect the application from monolith architecture to microservices architecture as  

![bookstore-tobe-asis-arch-v2](https://github.com/hinhvudinh/Simple-Application-Modernization-using-AWS-EKS/assets/18361892/df6fb2b0-0776-4e59-8586-c298289a2258) 



The figure on the left shows the current Book Store application that runs on-premise server and the right figure shows the migrated Book Store application on the AWS cloud platform. The migrated Book Store is developed by Spring boot framework and operate on a EKS (Elastics Kubernetes Cluster) that will automatic scale based on traffic. The H2 - Database is migrated to Amazon RDS MySQL for high-availability and loose couple with the application. With this microservice architecture, the application will ensure the high-availability, resilient and high performance. The hardware cost also reduced while it operated on public cloud platform.


For the explanation purpose, the engineer design a detail workflow solution for the app as follows:  

![bookstore-cicd-blog](https://github.com/hinhvudinh/Simple-Application-Modernization-using-AWS-EKS/assets/18361892/17df282b-9aaf-4b79-8acb-6dbe0cab982f)

As the figure above, the architecture categories into 2 modules: Development module and Operation module
In the development module, the engineer will:  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. Develop application code and push it to the Github  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. GitHub triggers Jenkins to start building process  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3. Jenkins will push the built image into Docker Hub  
In the operation module  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. Engineer uses terraform to provision VPC, Amazon RDS MySQL, EKS Cluster on AWS cloud platform  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. Setup ArgoCD in Elastic Kubernetes Cluster (EKS node)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3. Pull application image from Docker Hub  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4. Deploy the application as a pod of Kubernetes  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5. Access to the application via ELB endpoint  
End users then access the application via Elastic Load Balancer endpoint. Please follow this link for the source code:  

https://github.com/hinhvudinh/Simple-Application-Modernization  

This architecture ensure that the application meet the company need:  
- The development module applies continuous integration approach that ensure the seamless integration of code changes, promote collaboration, and maintain a high level of software quality.  
- The operation module use terraform tools to provision Elastic Kubernetes Cluster (EKS) and its convenient to management infrastructure. The Elastic Kubernetes Cluster (EKS) will automatically scale to meet the high traffic and operated without downtime.  


## Reference  
[1] [Java-Sping-Boot](https://www.ibm.com/topics/java-spring-boot#:~:text=Spring%20Boot%20helps%20developers%20create,app%20during%20the%20initialization%20process.)    
[2] https://spring.io/guides/gs/spring-boot/  
[3] https://aws.amazon.com/blogs/enterprise-strategy/6-strategies-for-migrating-applications-to-the-cloud/  
[4] https://www.vmware.com/topics/glossary/content/application-modernization.html  

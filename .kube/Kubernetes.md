1. install minikube https://minikube.sigs.k8s.io/docs/start/

   1. **choco install minikube** if you have Chocolatey
2. minikube start
3. in another CMD window **minikube dashboard**. Go back to the first CMD
4. minikube image load soa-project-user-profile & minikube image load soa-project-meal-planning-ui & minikube image load soa-project-inventorymanagement & minikube image load soa-project-jumbopriceservice & minikube image load soa-project-shoppinglistoptimization & minikube image load soa-project-mealplanningservice
5. cd xx/xx/.kube
6. kubectl apply -f activemq-service.yaml,inventorymanagement-service.yaml,jumbopriceservice-service.yaml,meal-planning-ui-service.yaml,mealplanningservice-service.yaml,profile-database-service.yaml,shoppinglistoptimization-service.yaml,user-profile-service.yaml,activemq-deployment.yaml,activemq-claim0-persistentvolumeclaim.yaml,activemq-claim1-persistentvolumeclaim.yaml,inventorymanagement-deployment.yaml,jumbopriceservice-deployment.yaml,meal-planning-ui-deployment.yaml,mealplanningservice-deployment.yaml,profile-database-deployment.yaml,profile-database-mongo-persistentvolumeclaim.yaml,shoppinglistoptimization-deployment.yaml,user-profile-deployment.yaml
7. minikube service soa-project-meal-planning-ui --url

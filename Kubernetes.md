1. install minikube https://minikube.sigs.k8s.io/docs/start/
   1. `choco install minikube` if you have Chocolatey
2. `minikube start`
3. in another CMD window `minikube dashboard`. Go back to the first CMD
4. `cd xx/xx/xx/SOA-project`
5. `kubectl apply -f activemq-service.yaml,inventorymanagement-service.yaml,jumbopriceservice-service.yaml,meal-planning-ui-service.yaml,mealplanningservice-service.yaml,profile-database-service.yaml,shoppinglistoptimization-service.yaml,user-profile-service.yaml,activemq-deployment.yaml,activemq-claim0-persistentvolumeclaim.yaml,activemq-claim1-persistentvolumeclaim.yaml,inventorymanagement-deployment.yaml,jumbopriceservice-deployment.yaml,meal-planning-ui-deployment.yaml,mealplanningservice-deployment.yaml,profile-database-deployment.yaml,profile-database-mongo-persistentvolumeclaim.yaml,shoppinglistoptimization-deployment.yaml,user-profile-deployment.yaml`

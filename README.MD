# Complete Setup Guide for Kubernetes Deployment on Minikube

This README provides detailed instructions on how to deploy the microservices defined in this project onto a Minikube Kubernetes cluster using images available from a Docker registry and Kubernetes for orchestration.

## Prerequisites

- **Minikube**: Minikube must be installed and running to host the Kubernetes cluster.
- **kubectl**: The Kubernetes command-line tool, kubectl, must be installed to manage Kubernetes clusters.

## Step 1: Start Minikube

Begin by starting Minikube, which simulates a Kubernetes cluster:

```bash
minikube start
```

## Step 2: Deploy Services using kubectl

With Minikube running and configured to pull images from your Docker registry, deploy your services using the Kubernetes configuration files stored in the `.kube` directory.

Navigate to the directory containing your Kubernetes configuration files and apply them:

```bash
kubectl apply -f ./.kube/
```

This command will deploy all the Kubernetes resources defined in your .kube folder, such as pods, services, deployments, etc.

## Step 3: Verify Deployment

To verify that your services have been deployed successfully, run the following command:

```bash
kubectl get pods
```

This command will list all the pods running in your Kubernetes cluster. If your services have been deployed successfully, you should see the pods listed.

## Step 4: Access Services

To access your services, you need to open a tunnel to the Minikube cluster:

```bash
minikube tunnel
```

This command will create a tunnel to the Minikube cluster, allowing you to access your services using the cluster's IP address.

## Step 5: Access UI

To access the UI service, open a web browser and navigate to the following URL:

```bash
http://<minikube-ip>:8085
```

Replace `<minikube-ip>` with the IP address of your Minikube cluster. If running on localhost, use `localhost`.


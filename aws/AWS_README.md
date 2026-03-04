# AWS lab deployment (EC2 + RDS) — Worklog API

This branch documents a **lab-style deployment** of Worklog API to AWS using:
- **EC2** as a Docker host running the application container
- **RDS PostgreSQL** as a managed database (private, not containerized)

Goal: demonstrate a simple, repeatable deployment pattern with minimal exposure and easy cleanup.

---

## Architecture (high level)

- Client (curl/browser/Swagger) → **EC2 (HTTP :8080)**
- EC2 → **RDS PostgreSQL (TCP :5432)** inside a **VPC**
- Admin access: **AWS Systems Manager Session Manager** → EC2 (no SSH required)

Key idea: DB is private; only the app instance can reach it.

---

## Security (least privilege)

Network-level:
- App security group: allow inbound **8080 only from a trusted IP** (e.g. your current public IP).
- DB security group: allow inbound **5432 only from the app security group**.
- RDS: **Public access = No**.

Access-level:
- EC2 uses an IAM role with **AmazonSSMManagedInstanceCore** to enable Session Manager access.

---

## Deployment outline (console-first)

1) Create security groups:
    - `sg-app`: 8080 from your IP
    - `sg-db`: 5432 from `sg-app`

2) Create RDS PostgreSQL:
    - Private (no public access)
    - Note the DB endpoint

3) Launch EC2 (Amazon Linux) with:
    - `sg-app`
    - IAM role for SSM (Session Manager)

4) Connect via **Session Manager** and run the container:
    - Pull image: `stefndock/worklog-api:<version>`
    - Start container with env vars:
        - `SPRING_DATASOURCE_URL=jdbc:postgresql://<RDS_ENDPOINT>:5432/<DB_NAME>`
        - DB username/password as variables

---

## Verification

- Local on EC2:
    - `curl http://localhost:8080/actuator/health`
- From your machine:
    - `curl http://<EC2_PUBLIC_IP>:8080/actuator/health`

Expected: HTTP 200 + `"status":"UP"` and DB status `"UP"`.

---

## Cleanup (avoid costs)

After the demo:
- Terminate EC2 instance
- Delete RDS database (avoid keeping snapshots/backups for lab use)
- Confirm no leftover RDS snapshots and unattached EBS volumes




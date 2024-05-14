<div align="center">
<img src="bg-accent_rounded.png" height="100" />
</div>

<h1 align="center">Bloggios (Auth Server)</h1>

<div align="center">
<img src="https://img.shields.io/badge/all_contributors-2-orange.svg?style=flat-square" />
</div>

<p align="center">
  <a href="https://github.com/Bloggios-Open-Source/auth-server/LICENSE" target="blank">
<img src="https://img.shields.io/github/license/Bloggios-Open-Source/auth-server?style=flat-square" alt="Bloggios-Open-Source licence" />
</a>
<a href="https://github.com/Bloggios-Open-Source/auth-server/fork" target="blank">
<img src="https://img.shields.io/github/forks/Bloggios-Open-Source/auth-server?style=flat-square" alt="Bloggios-Open-Source forks"/>
</a>
<a href="https://github.com/Bloggios-Open-Source/auth-server/stargazers" target="blank">
<img src="https://img.shields.io/github/stars/Bloggios-Open-Source/auth-server?style=flat-square" alt="Bloggios-Open-Source stars"/>
</a>
<a href="https://github.com/Bloggios-Open-Source/auth-server/issues" target="blank">
<img src="https://img.shields.io/github/issues/Bloggios-Open-Source/auth-server?style=flat-square" alt="Bloggios-Open-Source issues"/>
</a>
<a href="https://github.com/Bloggios-Open-Source/auth-server/pulls" target="blank">
<img src="https://img.shields.io/github/issues-pr/Bloggios-Open-Source/auth-server?style=flat-square" alt="Bloggios-Open-Source pull-requests"/>
</a>
</p>

### Introduction
The authentication server serves as the backbone for all authentication-related functions within a system. It handles essential tasks such as user login, registration, social login integration, cookie management, and one-time password (OTP) management.
User authentication, including login and registration processes, is efficiently managed by the auth server, ensuring secure access to the system's resources. It verifies user credentials, authenticates users, and issues access tokens or session identifiers upon successful authentication.
Moreover, the auth server seamlessly integrates social login functionality, allowing users to authenticate using their social media accounts such as Google, Facebook, or Twitter. This enhances user experience by providing alternative authentication methods and leveraging existing social media credentials.
Additionally, the auth server is responsible for managing user sessions and cookies. It generates and maintains session tokens or cookies to track user authentication status across multiple requests, ensuring a smooth and secure browsing experience for authenticated users.
Furthermore, the auth server handles OTP management, enabling the generation and validation of one-time passwords for additional security measures. This functionality is commonly used for two-factor authentication (2FA) or account recovery processes, adding an extra layer of security to user accounts.
Overall, the auth server plays a crucial role in ensuring the security and integrity of the system by managing authentication processes, integrating social login options, handling session management, and facilitating OTP-based authentication. Its robust functionality streamlines user authentication and enhances the overall security posture of the application.

## Application Documentation
https://www.postman.com/rohit-zip/workspace/bloggios/collection/34920421-dbc22257-2de7-4888-a0b1-69d0234bb3b4

### Software Required

- Java 17
- [IntelliJ IDEA](https://www.jetbrains.com/idea/) (or STS, Eclipse, Visual Studio Code)
- [PostgreSQL](https://www.postgresql.org/)
- [Elasticsearch](https://www.elastic.co/elasticsearch/)
- [Kafka](https://kafka.apache.org/) (if needed)

### 🍴 Fork and Clone the Repo

First, you need to fork the `auth-server` repo. You can do this by clicking the `Fork` button on the top right corner of the repo. If you are new to forking, please watch this [YouTube Guide](https://www.youtube.com/watch?v=h8suY-Osn8Q) to get started.

Once forked, you can clone the repo by clicking the `Clone or Download` button on the top right corner of the forked repo.

Please change the directory after cloning the repository using the `cd <folder-name>` command.

> **Note:** Please do not remove and update the `application.yml` and `pom.xml` file from the resources and root folder respectively. It contains all the environment variables and dependencies required for development.

### Open Project

Next, open the project in your favourite IDE. We recommended using Intellij Idea but you can use any.

Set the environment variables in your IDE. If you are new at setting environment variables the please go through the below videos
- Intellij Idea [See Video](https://www.youtube.com/watch?v=jNOh4jQJG2U)
- Eclipse or STS [See Video](https://www.youtube.com/watch?v=ypvGDkbp8Ac)

Required Environment Variables

```
BLOGGIOS_MAIL_API_KEY -> Give any value but remember to use same for other microservices too for intercommunication
BOOTSTRAP_SERVER -> Link to kafka broker server. You can start kafka broker locally (localhost:9092)
ES_PASSWORD -> Password of your Elasticsearch
ES_SERVER -> Elastic Search password Link (localhost:9200)
ES_USERNAME -> Elasticsearch Username
GOOGLE_CLIENT_ID -> Give any random value or if want to test Google OAuth Locally then generate client id and secret from Google Developer Console
GOOGLE_CLIENT_SECRET -> Give any random value or if want to test Google OAuth Locally then generate client id and secret from Google Developer Console
POSTGRES_DATABASE -> Postgresql Database name
POSTGRES_HOST -> Postgres Database Host
POSTGRES_PASSWORD -> Postgres Database Password
POSTGRES_USERNAME -> Postgresq Database Username
BASE_URL -> Base URL of other microservice as this used for intercommunication. If don't have then give any random value
```

## 🤝 Contributing to `Bloggios`

Any kind of positive contribution is welcome! Please help us to grow by contributing to the project.

If you wish to contribute, you can,

- Star Repositories Bloggios
- Suggest a Feature
- Test the application, and help it improve.
- Improve the app, fix bugs, etc.
- Improve documentation.
- Create content about Bloggios and share it with the world.

> Please read [`CONTRIBUTING`](CONTRIBUTING.md) for details on our [`CODE OF CONDUCT`](CODE_OF_CONDUCT.md), and the process for submitting pull requests to us.

🆕 New to Open Source? 💡 Follow this [guide](https://opensource.guide/how-to-contribute/) to jumpstart your Open Source journey 🚀.

## 🙏 Support

We all need support and motivation. `Bloggios` is not an exception. Please give this project repositories a ⭐️ to encourage and show that you liked it. Don't forget to leave a star ⭐️ before you move away.

If you found the app helpful, consider supporting us with a coffee.

## Contributors ✨

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/rohit-zip"><img src="https://avatars.githubusercontent.com/u/75197401?v=4" width="100px;" alt="Rohit Parihar"/><br /><sub><b>Rohit Parihar</b></sub></a><br /><a href="https://github.com/rohit-zip" title="Code">💻</a></td>
    </tr>
  </tbody>
</table>
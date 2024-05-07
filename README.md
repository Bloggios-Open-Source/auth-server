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
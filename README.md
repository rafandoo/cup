<h1 align="center">Coffee Utilities Package ☕</h1>

> Documentation: [📖 JavaDoc](https://rafandoo.github.io/cup/)

## 📝 Project description

<p align="justify">
Coffee Utilities Package (CUP) is a Java library that provides a set of utilities for Java developers. The main 
objective of this project is to facilitate the development of Java applications, providing a set of tools that can be 
used in various contexts. The project is divided into modules, each of which provides a set of utilities for a specific 
context. The modules are independent of each other, so you can use only the modules you need in your project. The 
project is open source and is available on GitHub. You can contribute to the project by submitting bug reports, feature 
requests, or code contributions. The project is licensed under the Apache License 2.0.
</p>

## 🤔 Problem definition

<p align="justify">
Java is a powerful programming language that is widely used in the software development industry. However, Java does 
not provide a complete set of utilities for developers. Developers often need to write their own utilities to perform 
common tasks such as reading and writing files, working with dates and times, and working with collections. This can be 
time-consuming and error-prone. The Coffee Utilities Package (CUP) project aims to provide a set of utilities that can 
be used by Java developers to perform common tasks more easily and efficiently.
</p>

## 🛠️ Technologies used

<p align="center">
    <img src="https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=gradle&logoColor=white" alt="Gradle"/>
    <img src="https://img.shields.io/badge/Java-ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
</p>

## 📦 Installation & Usage

### 🧩 Available Modules

This project is modular. You can include the complete package or just specific modules, depending on your needs:

| Module    | Artifact ID | Description                               |
|-----------|-------------|-------------------------------------------|
| core      | cup-core    | General-purpose utilities, logging, enums |
| http      | cup-http    | HTTP request helpers and URL builder      |
| objects   | cup-objects | Object serialization, file exporters      |
| (planned) | cup-files   | File utilities and resource management    |

### ☁️ How to Add to Your Project

You can include the dependencies in two different ways, depending on your preference:

#### 🔐 Option 1: GitHub Packages (requires authentication)

If you are using Gradle, add the GitHub Packages repository to your `build.gradle`:

```groovy
repositories {
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/rafandoo/cup")
        credentials {
            username = project.findProperty("gpr.user") as String ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") as String ?: System.getenv("TOKEN")
        }
    }
}

dependencies {
    implementation 'br.dev.rplus:cup-core:{LAST_VERSION}'
}
```

If you are using Maven, add the following to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/rafandoo/cup</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
        <releases>
            <enabled>true</enabled>
        </releases>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>br.dev.rplus</groupId>
        <artifactId>cup-core</artifactId>
        <version>{LAST_VERSION}</version>
    </dependency>
</dependencies>
```

You must also include your GitHub credentials in your `settings.xml`:

```xml
<settings>
    <servers>
        <server>
            <id>github</id>
            <username>USERNAME</username>
            <password>TOKEN</password>
        </server>
    </servers>
</settings>
```

> ℹ️ Replace {LAST_VERSION} with the latest release (e.g. 1.0.0).

#### 🌐 Option 2: Public Raw Repository (no authentication)

If you don't want to use GitHub Packages, you can use the raw GitHub Pages Maven repository.
This does not require authentication, and works for public consumption.

For Gradle:

```groovy
repositories {
    maven { 
        url 'https://raw.githubusercontent.com/rafandoo/cup/mvn-repo/' 
    }
}

dependencies {
    implementation 'br.dev.rplus:cup-core:{LAST_VERSION}'
}
```

For Maven:

```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://raw.githubusercontent.com/rafandoo/cup/mvn-repo/</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
        <releases>
            <enabled>true</enabled>
        </releases>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>br.dev.rplus</groupId>
        <artifactId>cup-core</artifactId>
        <version>{LAST_VERSION}</version>
    </dependency>
</dependencies>
```

### 🧪 Local Development

Clone the repository and build locally:

```bash
git clone https://github.com/rafandoo/cup.git
cd cup

./gradlew build
```

## 🔧 Functionalities

✔️ General utilities.

✔️ Environment utilities.

✔️ Logger manager.

✔️ HTTP class for requests.

✔️ Object serialization and export in different file formats.

✔️ Unique identifier generator.

✔️ Enum utilities.

## 🚀 Future enhancements

✔️ File utilities.

✔️ Cryptography utilities.

✔️ Resource management.

✔️ And more...

## 🔑 License

The [Apache License 2.0](https://github.com/rafandoo/cup/blob/87b6388949953738ce3b39148d2628923fe3c139/LICENSE)

Copyright :copyright: 2024-present - Rafael Camargo

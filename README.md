# AI-Bot Discord Integration

## Overview

This project integrates an AI chatbot into a Discord bot using OpenAI's GPT-3.5-Turbo model. Users can interact with the bot by asking questions, and the bot responds using the OpenAI API.

## Features

- Slash command `/ask` to ask the AI bot a question.
- Rate-limiting and token count management to stay within OpenAI's usage limits.
- Error handling and exponential backoff for rate-limited requests.

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- [Maven](https://maven.apache.org/) for dependency management
- OpenAI API Key
- Discord Bot Token

## Getting Started

### Clone the Repository

```bash
git clone <repository-url>
cd <repository-directory>
```

### Configuration

Create a `.env` file in the root of your project and add your API keys:
OPENAI_API_KEY=your_openai_api_key DISCORD_BOT_TOKEN=your_discord_bot_token

Replace `your_openai_api_key` and `your_discord_bot_token` with your actual OpenAI API key and Discord bot token.

### Building the Project

This project uses Maven for dependency management and building. To build the project, navigate to the project directory and run:

```bash
mvn clean install
```

### Running the Bot

After building the project, you can run the bot using the following command:

```bash
java -jar target/your-jar-file.jar
```

Replace `your-jar-file.jar` with the name of the JAR file created during the build process.

## Project Structure

- `src/main/java/com/github/spacemex/api`: Contains the OpenAI client for interacting with OpenAI's API.
- `src/main/java/com/github/spacemex/util`: Contains utility classes and the command manager for processing Discord commands.

## Usage

Once the bot is running, you can ask questions using the slash command `/ask` in your Discord server. The bot will respond with an answer generated by OpenAI's GPT-3.5-Turbo model.

### Example

In Discord:

/ask How can I integrate OpenAI with a Discord bot?

`Bot's response:`

To integrate OpenAI with a Discord bot, you need to...

## Dependencies

Add the following dependencies in your `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>net.dv8tion</groupId>
        <artifactId>JDA</artifactId>
        <version>5.0.0-alpha.12</version>
    </dependency>
    <dependency>
        <groupId>com.squareup.okhttp3</groupId>
        <artifactId>okhttp</artifactId>
        <version>4.9.0</version>
    </dependency>
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.8.6</version>
    </dependency>
</dependencies>
```

## Build Configuration

Add the following build configuration in your `pom.xml`:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.1</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-jar-plugin</artifactId>
            <version>3.1.0</version>
            <configuration>
                <archive>
                    <manifest>
                        <mainClass>com.github.spacemex.MainClass</mainClass>
                    </manifest>
                </archive>
            </configuration>
        </plugin>
    </plugins>
</build>
```

Replace `com.github.spacemex.MainClass` with the actual main class name of your project.

## Contributing

Contributions are welcome! Please fork this repository and submit a pull request for review.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.
import json
import openai

# Your OpenAI API key
openai.api_key = "YOUR_API_KEY"

# Load JSON data from a file
with open("your_json_file.json", "r") as f:
    json_data = json.load(f)

# Calculate token counts
formatted_tokens = openai.ChatCompletion.create(
    model="gpt-3.5-turbo",
    messages=[
        {"role": "user", "content": json.dumps(json_data, indent=4)}
    ]
).usage.total_tokens

unformatted_tokens = openai.ChatCompletion.create(
    model="gpt-3.5-turbo",
    messages=[
        {"role": "user", "content": json.dumps(json_data)}
    ]
).usage.total_tokens

# Print results
print(f"Formatted JSON tokens: {formatted_tokens}")
print(f"Unformatted JSON tokens: {unformatted_tokens}")
package org.example.tasklist.config;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class LocalDateTimeCoercing implements Coercing<LocalDateTime, String> {
    @Nullable
    @Override
    public String serialize(
            @NotNull final Object dataFetcherResult,
            @NotNull final GraphQLContext graphQlContext,
            @NotNull final Locale locale
    ) {
        SimpleDateFormat formatter
                = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
                Locale.ENGLISH);
        return formatter.format(
                Date.from(((LocalDateTime) dataFetcherResult)
                        .atZone(ZoneId.systemDefault())
                        .toInstant())
        );
    }

    @Nullable
    @Override
    public LocalDateTime parseValue(
            @NotNull Object input,
            @NotNull GraphQLContext graphQlContext,
            @NotNull Locale locale
    ) throws CoercingParseValueException {
        return LocalDateTime.parse((String) input);
    }

    @Nullable
    @Override
    public LocalDateTime parseLiteral(
            @NotNull Value<?> input,
            @NotNull CoercedVariables variables,
            @NotNull GraphQLContext graphQlContext,
            @NotNull Locale locale
    ) throws CoercingParseLiteralException {
        return LocalDateTime.parse(((StringValue) input).getValue());
    }
}
